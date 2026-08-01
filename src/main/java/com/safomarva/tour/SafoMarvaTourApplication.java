package com.safomarva.tour;

import com.safomarva.tour.model.PackageEntity;
import com.safomarva.tour.repository.PackageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class SafoMarvaTourApplication {

    public static void main(String[] eloquenceArgs) {
        SpringApplication.run(SafoMarvaTourApplication.class, eloquenceArgs);
    }

    private static final Set<String> ACTIVE_PACKAGE_KEYS = Set.of(
            "special_14day", "comfort_plus_10", "standard_13",
            "anjum_lux", "jumeirah_lux");
    private static final Set<String> RETIRED_PACKAGE_KEYS = Set.of("lux", "lux_premium", "lux_jumeirah", "al_jabri_14");

    @Bean
    public CommandLineRunner syncPackages(PackageRepository packageRepository) {
        return args -> {
            System.out.println("📦 Paketlar bazasi sayt bilan sinxronlanmoqda...");

            upsertPackage(packageRepository, "comfort_plus_10", "Comfort Plus (10 kunlik)", "1390",
                    "Iyul, Avgust va Sentyabr oylari uchun SAFO MARVA TOUR ning \"Comfort Plus\" 10 kunlik paketi\n\nSAFO MARVA TOUR bilan — litsenziyalangan, xavfsiz, xotirjam\n\nCHIQISH SANALARI (har payshanba):\n16.07.2026 (11:00 - 16:20) · 23.07 · 30.07\n06.08 · 13.08 · 20.08 · 27.08\n03.09 · 10.09 · 17.09 · 24.09\n\nKETISH: Toshkent - Madina\nQAYTISH: Jidda - Toshkent\n\n1 Juma Madinada + 1 Juma Makkada\n\nMadinada: Nusk Al Hijra / Golden Shakereen / Sebal al Masi 4★, 3 kecha\nMakkada: Al Ebaa hotel 4★, 6 kecha\n\nXona narxlari:\n4 kishilik - 1390$\n3 kishilik - 1490$\n2 kishilik - 1590$\n\nPaketga kiradi:\nAviachipta\nViza va sug'urta\nMehmonxona\nTransfer\nTezyurar poyezd\n2 mahal ovqat\nEkskursiyalar\nRavza ziyorati\nIlmli guruh rahbarlari\nShifokor xizmati\nQizil dengiz sayohati\n\nHadyalar:\nZam-Zam 5 litr\nNimcha\nSumka\nBeyjik\nAbaya (ayollar uchun)\n\nMurojaat uchun:\n+998 55 517 73 73\n@Safomarva_admin");
            upsertPackage(packageRepository, "special_14day", "Comfort Plus (14 kunlik)", "1550",
                    "Avgust va Sentyabr oylari uchun SAFO MARVA TOUR ning \"Comfort Plus\" 14 kunlik paketi\n\nSAFO MARVA TOUR bilan — litsenziyalangan, xavfsiz, xotirjam\n\nKETISH SANALARI:\n01.08.2026 - 15.08.2026\n15.08.2026 - 29.08.2026\n29.08.2026 - 12.09.2026\n\nYO'NALISH 1: Toshkent - Madina - Toshkent\nYO'NALISH 2: Toshkent - Jidda - Toshkent\n\n1 Juma Madinada + 1 Juma Makkada\n\nMadinada: Mehrob Toiba 4★, 7 kecha\nMakkada: Al Ebaa hotel 4★, 7 kecha\n\nXona narxlari:\n4 kishilik - 1550$\n3 kishilik - 1650$\n2 kishilik - 1800$\n\nPaketga kiradi:\nTezyurar poyezd xizmati\n2 mahal taom - Madinada\n2 mahal taom - Makkada\nMakka va Madina bo'ylab ekskursiyalar\nRavzaga kirish imkoniyati\nIlmli guruh rahbarlari xizmatlari\nMalakali shifokor xizmatlari\nQizil dengiz sayohati\n\nHadyalar:\nZam-Zam 5 litr\nNimcha\nSumka\nBeyjik\nAbaya (ayollar uchun)\n\nMurojaat uchun:\n+998 55 517 73 73\n@Safomarva_admin");

            upsertPackage(packageRepository, "jumeirah_lux", "Jumeirah Premium LUX (10 kunlik)", "1750",
                    "Avgust, Sentyabr, Oktyabr, Noyabr va Dekabr oylari uchun SAFO MARVA TOUR ning \"Jumeirah\" 10 kunlik Premium LUX paketi\n\nSAFO MARVA TOUR bilan — litsenziyalangan, xavfsiz, xotirjam\n\nKETISH: Toshkent - Madina\nQAYTISH: Jidda - Toshkent\n\n1 Juma Madinada + 1 Juma Makkada\n\nMadinada: Waqf as Safi 5★, 4 kecha\nMakkada: Jumeirah Jabal Omar 5★, 5 kecha\n\nKETISH SANALARI:\n20.08.2026 (11:00 - 16:20) · 03.09 · 17.09\n01.10.2026 (11:00 - 16:20) · 15.10 · 29.10 · 12.11 · 26.11 · 10.12\n\nXONA NARXLARI (20.08 - 17.09):\n4 kishilik - 1750$\n3 kishilik - 1850$\n2 kishilik - 2050$\n\nXONA NARXLARI (01.10 - 10.12):\n4 kishilik - 1850$\n3 kishilik - 1950$\n2 kishilik - 2150$\n\nPaketga kiradi:\nTezyurar poyezd xizmati\n2 mahal taom - Madinada\n2 mahal taom - Makkada\nMakka va Madina bo'ylab ekskursiyalar\nRavzaga kirish imkoniyati\nIlmli guruh rahbarlari xizmatlari\nMalakali shifokor xizmatlari\nQizil dengiz sayohati\n\nHadyalar:\nZam-Zam 5 litr\nNimcha\nSumka\nBeyjik\nAbaya (ayollar uchun)\n\nMurojaat uchun:\n+998 94 625 77 72\n@MuhammadYusuf_ilm\n@SafoMarva_Umra_VIP");
            upsertPackage(packageRepository, "anjum_lux", "ANJUM LUX OTA-ONALAR (10 kunlik)", "1650",
                    "Avgust oyi uchun SAFO MARVA TOUR ning \"ANJUM\" 10 kunlik LUX OTA-ONALAR paketi\n\nSAFO MARVA TOUR bilan — litsenziyalangan, xavfsiz, xotirjam\n\nKETISH: Toshkent - Madina\nQAYTISH: Jidda - Toshkent\n\n1 Juma Madinada + 1 Juma Makkada\n\nMadinada: Waqf as Safi 5★, 4 kecha\nMakkada: Anjum Makkah 5★, 5 kecha\n\nKETISH SANALARI:\n13.08.2026 (11:00 - 16:20) · 27.08 · 10.09 · 24.09\n08.10.2026 (11:00 - 16:20) · 22.10 · 05.11 · 19.11 · 03.12\n\nXONA NARXLARI (13.08 - 24.09):\n4 kishilik - 1650$\n3 kishilik - 1750$\n2 kishilik - 1950$\n\nXONA NARXLARI (08.10 - 03.12):\n4 kishilik - 1750$\n3 kishilik - 1850$\n2 kishilik - 2050$\n\nPaketga kiradi:\nTezyurar poyezd xizmati\n2 mahal taom - Madinada\n2 mahal taom - Makkada\nMakka va Madina bo'ylab ekskursiyalar\nRavzaga kirish imkoniyati\nIlmli guruh rahbarlari xizmatlari\nMalakali shifokor xizmatlari\nQizil dengiz sayohati\n\nHadyalar:\nZam-Zam 5 litr\nNimcha\nSumka\nBeyjik\nAbaya (ayollar uchun)\n\nMurojaat uchun:\n+998 55 517 73 73\n@MuhammadYusuf_ilm\n@SafoMarva_Umra_VIP");
            upsertPackage(packageRepository, "standard_13", "Standard (13 kunlik)", "990",
                    "Avgust va Sentyabr oylari uchun SAFO MARVA TOUR ning 13 kunlik \"Standard\" paketi\n\nSAFO MARVA TOUR bilan — litsenziyalangan, xavfsiz, xotirjam\n\nKETISH SANALARI (har shanba):\n01.08.2026 (05:20 - 10:40) · 08.08 · 15.08 · 22.08 · 29.08.2026\n\nKETISH: Toshkent - Jidda\nQAYTISH: Madina - Toshkent\n\nMakkada: Toreeq al Hijra hotel, 9 kecha\nMadinada: Mehrob Toiba, 3 kecha\n\nXona narxlari (Viza bor bo'lsa):\n4 kishilik - 990$\n3 kishilik - 1090$\n2 kishilik - 1190$\n\nXona narxlari (Viza bilan):\n4 kishilik - 1090$\n3 kishilik - 1190$\n2 kishilik - 1290$\n\nPaketga kiradi:\nAviachipta\nMehmonxona\nTransfer\nTezyurar poyezd\n2 mahal taom - Madinada\n3 mahal taom - Makkada\nMakka va Madina bo'ylab ekskursiyalar\nRavzaga kirish imkoniyati\nIlmli guruh rahbarlari xizmatlari\nMalakali shifokor xizmatlari\n\nHadyalar:\nZam-Zam 5 litr\nNimcha\nSumka\nBeyjik\nAbaya (ayollar uchun)\n\nMurojaat uchun:\n+998 55 517 73 73\n@Safomarva_admin");
            packageRepository.findAll().stream()
                    .filter(pkg -> RETIRED_PACKAGE_KEYS.contains(pkg.getKeyName()))
                    .forEach(pkg -> {
                        packageRepository.delete(pkg);
                        System.out.println("🗑 Eski paket o'chirildi: " + pkg.getKeyName() + " (" + pkg.getDisplayName() + ")");
                    });

            System.out.println("✅ Paketlar sinxronlandi. Faol paketlar: " + ACTIVE_PACKAGE_KEYS);
        };
    }

    private void upsertPackage(
            PackageRepository packageRepository,
            String keyName,
            String displayName,
            String price,
            String description) {
        PackageEntity pkg = packageRepository.findByKeyName(keyName)
                .orElseGet(() -> new PackageEntity(keyName, displayName, price, description));

        pkg.setDisplayName(displayName);
        pkg.setPrice(price);
        pkg.setDescription(description);
        packageRepository.save(pkg);
    }
}
