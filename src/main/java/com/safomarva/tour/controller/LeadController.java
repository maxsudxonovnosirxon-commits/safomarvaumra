package com.safomarva.tour.controller;

import com.safomarva.tour.model.LeadEntity;
import com.safomarva.tour.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class LeadController {

    private final LeadRepository leadRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${bot.leadToken}")
    private String leadBotToken;

    @Value("${bot.admins}")
    private String adminsStr;

    public LeadController(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    // Admin API: Get all leads (for web admin panel)
    @RequestMapping(value = "/leads", method = RequestMethod.GET)
    public ResponseEntity<?> getLeads(@RequestHeader(value = "X-Admin-Key", required = false) String adminKey) {
        if (adminKey == null || !adminKey.equals("safo_marva_secret_admin_key_2026")) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }
        List<LeadEntity> leads = leadRepository.findAllByOrderByCreatedAtDesc();
        return ResponseEntity.ok(leads);
    }

    // Public API: Capture landing page lead registration form
    @PostMapping("/leads")
    public ResponseEntity<?> createLead(@RequestBody Map<String, Object> body) {
        String name = body.get("name") != null ? String.valueOf(body.get("name")) : null;
        String phone = body.get("phone") != null ? String.valueOf(body.get("phone")) : null;
        String selectedPackage = body.get("package") != null ? String.valueOf(body.get("package")) : null;
        String selectedPackageKey = body.get("packageKey") != null ? String.valueOf(body.get("packageKey")) : selectedPackage;
        String selectedPackageName = body.get("packageName") != null ? String.valueOf(body.get("packageName")) : selectedPackage;
        String room = body.get("room") != null ? String.valueOf(body.get("room")) : null;
        String operator = body.get("operator") != null ? String.valueOf(body.get("operator")) : null;
        String paymentMethod = body.get("paymentMethod") != null ? String.valueOf(body.get("paymentMethod")) : null;
        
        // Safely extract persons count
        int finalPersons = 1;
        if (body.containsKey("persons") && body.get("persons") != null) {
            try {
                finalPersons = Integer.parseInt(String.valueOf(body.get("persons")));
            } catch (NumberFormatException e) {
                System.err.println("⚠️ Could not parse persons value: " + body.get("persons"));
            }
        }

        // Basic Validation
        if (name == null || phone == null || selectedPackageKey == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Name, phone, and package are required."));
        }

        // Clean and sanitize input values
        String cleanName = name.trim().replaceAll("[<>]", "");
        String cleanPhone = phone.trim().replaceAll("[^+0-9]", "");
        if (!cleanPhone.startsWith("+")) {
            cleanPhone = "+" + cleanPhone;
        }
        String finalRoom = room != null ? room.trim() : "Kiritilmagan";
        String finalOperator = operator != null ? operator.trim() : "Erkak";
        String finalPayment = paymentMethod != null ? paymentMethod.trim() : "Naqd pul";
        String cleanPackageKey = selectedPackageKey.trim().replaceAll("[^A-Za-z0-9_\\-]", "");
        String cleanPackageName = selectedPackageName != null
                ? selectedPackageName.trim().replaceAll("[<>]", "")
                : cleanPackageKey;
        if (cleanPackageKey.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Package key is required."));
        }

        boolean dbSaved = false;
        try {
            // 1. Save to PostgreSQL database
            LeadEntity lead = new LeadEntity(cleanName, cleanPhone, cleanPackageKey, finalRoom, "web-site");
            lead.setOperator(finalOperator);
            lead.setPaymentMethod(finalPayment);
            lead.setPersons(finalPersons);
            leadRepository.save(lead);
            dbSaved = true;
            System.out.println("✅ Lead successfully saved to PostgreSQL via Spring JPA: " + cleanName);
        } catch (Exception e) {
            System.err.println("⚠️ PostgreSQL lead insertion warning: " + e.getMessage());
        }

        // 2. Dispatch Telegram Notification to Administrators
        try {
            String[] adminIds = adminsStr.split(",");
            String now = LocalDateTime.now(ZoneId.of("Asia/Tashkent")).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            String roomInfo = ("special_14day".equalsIgnoreCase(cleanPackageKey)
                    || "anjum_lux".equalsIgnoreCase(cleanPackageKey)
                    || "jumeirah_lux".equalsIgnoreCase(cleanPackageKey))
                    ? "\n🛏 <b>Xona turi:</b> " + finalRoom
                    : "";

            String message = "🌟 <b>YANGI MUROJAAT!</b> 🌟\n\n" +
                    "👤 <b>Mijoz:</b> " + cleanName + "\n" +
                    "📞 <b>Telefon:</b> <code>" + cleanPhone + "</code>\n" +
                    "📦 <b>Tanlangan paket:</b> " + cleanPackageName + roomInfo + "\n" +
                    "👥 <b>Ziyoratchilar soni:</b> " + finalPersons + " kishi\n" +
                    "🎧 <b>Operator jinsi:</b> " + finalOperator + "\n" +
                    "💳 <b>To'lov turi:</b> " + finalPayment + "\n\n" +
                    "📅 <b>Vaqt:</b> " + now + "\n" +
                    "🌐 <b>Manba:</b> Safo Marva Tour (Veb-sayt)\n\n" +
                    "📞 <b>Qo'ng'iroq uchun:</b> <a href=\"tel:" + cleanPhone.replaceAll("\\s", "") + "\">" + cleanPhone + "</a>";

            String telegramUrl = "https://api.telegram.org/bot" + leadBotToken + "/sendMessage";

            for (String adminId : adminIds) {
                String trimmedId = adminId.trim();
                if (trimmedId.isEmpty()) continue;

                // Send request asynchronously/separately
                new Thread(() -> {
                    try {
                        Map<String, Object> request = new HashMap<>();
                        request.put("chat_id", trimmedId);
                        request.put("text", message);
                        request.put("parse_mode", "HTML");

                        restTemplate.postForObject(telegramUrl, request, String.class);
                    } catch (Exception e) {
                        System.err.println("❌ Failed to send Telegram notification to admin " + trimmedId + ": " + e.getMessage());
                    }
                }).start();
            }
        } catch (Exception e) {
            System.err.println("❌ Telegram notification dispatch failed: " + e.getMessage());
        }

        return ResponseEntity.ok(Map.of("success", true, "dbSaved", dbSaved, "message", "Booking lead successfully processed."));
    }
}
