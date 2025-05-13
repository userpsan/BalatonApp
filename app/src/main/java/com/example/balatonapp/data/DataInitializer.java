package com.example.balatonapp.data;

public class DataInitializer {

    public static void populate(AppDatabase db) {

        db.newsDao().insert(new News(
                "Planet Lens fotófesztivál",
                "A veszprémi Planet Lens fesztivál májusban különleges képekkel érkezik.",
                "2025-04-30",
                "news_1",
                "https://welovebalaton.hu/cikk/2025/04/30/planet-lens-termesztfilm-fotofesztival-veszprem-2025-majus/"
        ));

        db.newsDao().insert(new News(
                "Balatoni magyar filmek toplistája",
                "Ezek a filmek mutatják be legjobban a Balaton hangulatát.",
                "2025-04-25",
                "news_2",
                "https://welovebalaton.hu/toplista/balaton-magyar-film/"
        ));

        db.newsDao().insert(new News(
                "Tavaszi kincskeresés",
                "Indul a tavaszi kaland a Balaton partján: keresd a kincseket!",
                "2025-04-20",
                "news_3",
                "https://balatonihirek.hu/indul-a-tavaszi-kincskereses-a-balatonnal/"
        ));

        db.newsDao().insert(new News(
                "Kömanok a parton",
                "Légy részese az egyensúly művészetének Balatonnál.",
                "2025-04-18",
                "news_4",
                "https://balatonihirek.hu/komanok-a-balaton-partjan-az-egyensuly-muveszete/"
        ));

        db.newsDao().insert(new News(
                "AktívBalaton programajánló",
                "A legmenőbb balatoni programok egy helyen!",
                "2025-04-10",
                "news_5",
                "https://balatonihirek.hu/aktivbalaton-a-legmenobb-balatoni-programok-egy-helyen/"
        ));

        db.newsDao().insert(new News(
                "Vaddisznó úszkált a Balatonban\n",
                "Váratlan látogatók a Balaton partján: a szezon után egyre több vadállat tűnik fel a csendes tóparton.",
                "2025-04-08",
                "news_6",
                "https://balatonihirek.hu/3347-2/"
        ));

        db.newsDao().insert(new News(
                "Őshonos fák program",
                "A Balaton fája program őshonos növényekkel újul meg.",
                "2025-04-05",
                "news_7",
                "https://balatonihirek.hu/oshonos-fak-a-balaton-faja-programban/"
        ));

        db.eventDao().insert(new Event(
                "35. MEDIAWAVE Nemzetközi Film és Zenei Együttlét a Balatonon - 2025",
                "A MEDIAWAVE Nemzetközi Film és Zenei Együttlét július 1-4. között a Szigliget Várudvarba költözik! A filmek mellé izgalmas zenei és kulturális kínálattal készülnek a szervezők. Itt a helyetek!",
                "2025-07-01",
                "Szigliget",
                "event_mediawave"
        ));

        db.eventDao().insert(new Event(
                "RUMBA Fesztivál 2025 - Rum, Bor és Asztal",
                "Egy pohár bor. Egy másik ritmus. A RUMBA egy élményalapú fesztivál, ahol lesz idő kóstolni, felfedezni, beszélgetni. Ha szeretitek az igényes borokat, rumokat, a különleges ételeket és a lenyűgöző helyszíneket, itt a helyetek július 23-24. között Tagyonban!",
                "2025-07-23",
                "Tagyon",
                "event_rum"
        ));

        db.eventDao().insert(new Event(
                "XIX. NN ULTRABALATON - 2025",
                "2025-ben 19. alkalommal rajtol el Magyarország legnépszerűbb közösségi sporteseménye, az NN Ultrabalaton. A Balatont megkerülő egykörös verseny egyben Közép-Európa leghosszabb futóversenye is, amelyet egyéniben, váltóban, futva vagy bringával is teljesíthetnek a résztvevők.",
                "2025-06-24",
                "Balatonfüred",
                "event_ub"
        ));

        db.eventDao().insert(new Event(
                "43. Lidl balaton-átúszás #megúszod?",
                "Magyarország legnagyobb nyíltvízi úszóeseménye Téged is vár. Csatlakozz a több, mint tízezer sportolóhoz, akik minden évben meghódítják a magyar tengert, a csodálatos Balatont! Az 5,2 kilométeres táv mellett teljesítheted a féltávot is (2,6 km) sőt SUP-pal is áthúzhatod Magyarország legnagyobb tavát. Viszlát a vízben!",
                "2025-07-26",
                "Fonyód",
                "event_atuszas"
        ));

        db.eventDao().insert(new Event(
                "57. Kékszalag 2025 Balatonfüred",
                "A Balaton és a vitorlázás legkiemelkedőbb ünnepe, Európa legnagyobb tókerülő vitorlásversenye, az 57. Kékszalag, 2025. július 10-én, 9 órakor rajtol el Balatonfüredről. A Kékszalag egy életre szóló élmény azoknak, akik elindulnak a versenyen, és hatalmas látványosság azoknak, akik csak a partról nézik és szurkolnak.",
                "2025-07-10",
                "Siófok",
                "event_kekszalag"
        ));

        db.eventDao().insert(new Event(
                "34. Művészetek Völgye",
                "Július 18-27. között vár mindenkit a 34. Művészetek Völgye, az ország legnagyobb összművészeti fesztiválja! A 10 napos rendezvény idén is a 3 balaton-felvidéki kis falu, Kapolcs, Taliándörög és Vígántpetend utcáit, köztereit, udvarait varázsolja koncerttéré, galériává és színházzá, megtöltve azokat közel 3.000 programmal.",
                "2025-07-18",
                "Kapolcs",
                "event_muveszetek"
        ));

        db.eventDao().insert(new Event(
                "13. Paloznaki JazzPiknik",
                "2025 július 31 és augusztus 2. között ismét fényekbe, hangjegyekbe, ízekbe és élményekbe borul a Balaton-felvidéki jazzfalu: jön a 13. Paloznaki JazzPiknik! A népszerű fesztiválon olyan világsztárokat köszönthetünk a színpadon, mint az 5 milliárd Spotify streamnél járó Lukas Graham, vagy a francia sanzonénekes sztár ZAZ, valamint visszatérnek olyan közönségkedvenc előadók, mint az Earth, Wind & Fire Experience, a Deladap, vagy Mario Biondi.",
                "2025-07-31",
                "Paloznak",
                "event_jazz"
        ));

        db.eventDao().insert(new Event(
                "Everness Fesztivál 2025 Siófok-Sóstó",
                "Everness Fesztivál 2025. június 24-30-ig, a siófoki Sóstón. Koncertek, meditációk, előadások, jóga, workshopok, egy balatoni program, ami egyet jelent a zenével, a mozgással, a színek, ízek és illatok mámorával. A népszerű rendezvény olyan felejthetetlen együttlét, ahol a tudatos pozitív gondolkodás és az egészséges életmód jegyében a hozzánk hasonlóan gondolkodók körében kapcsolódhatunk ki.",
                "2025-06-24",
                "Siófok",
                "event_everness"
        ));


        db.sightDao().insert(new Sight(
                "Bácsi-kápolna",
                "Káptalantótitól másfél kilométerre, a Tapolcai- és a Káli-medence találkozásánál találjuk a Szűz Mária tiszteletére építtetett Bácsi-kápolnát...",
                "Káptalantóti",
                "sight_bacsi_kapolna_kaptalantoti"
        ));

        db.sightDao().insert(new Sight(
                "Bazaltorgonák tanösvény",
                "A Szent György-hegyi Bazaltorgonák tanösvény egy 4 km hosszú, hétállomásos túraútvonal, amely bemutatja a környék földtani és természeti értékeit.",
                "Raposka",
                "sight_bazaltorgonak_tanosveny_raposka"
        ));

        db.sightDao().insert(new Sight(
                "Béke-sztúpa",
                "A zalaszántói Béke-sztúpa Európa egyik legnagyobb buddhista sztúpája. Nyugalmat és lelki feltöltődést kínál a látogatóknak.",
                "Zalaszántó",
                "sight_beke_sztupa_zalaszanto"
        ));

        db.sightDao().insert(new Sight(
                "Benedek-hegyi kereszt",
                "A veszprémi Szent Benedek-hegy keresztje egy kedvelt kilátópont, ahonnan páratlan panoráma nyílik a Bakonyra.",
                "Veszprém",
                "sight_benedek_hegyi_kereszt_veszprem"
        ));

        db.sightDao().insert(new Sight(
                "Boldogasszony-templomrom",
                "A Gernye-hegyen található templomrom háromemeletes, ikerablakos tornyával és panorámás kilátójával remek piknikezőhely.",
                "Dörgicse",
                "sight_boldogasszony_templomrom_dorgicse"
        ));

        db.sightDao().insert(new Sight(
                "Cseszneki vár",
                "A Bakony egyik legszebb romvára, amely történelmi múltjával és kilátásával méltán népszerű kirándulóhely.",
                "Csesznek",
                "sight_cseszneki_var"
        ));

        db.sightDao().insert(new Sight(
                "Csodabogyós-barlang",
                "A Balaton-felvidéki Nemzeti Park védett barlangja, ahol szervezett túrák keretében járható be a cseppköves világ.",
                "Balatonederics",
                "sight_csodabogyos_barlang_balatonederics"
        ));

        db.sightDao().insert(new Sight(
                "Csobánc vára",
                "A Csobánc hegy tetején fekvő várrom páratlan panorámát kínál a tanúhegyekre és a Tapolcai-medencére.",
                "Csobánc",
                "sight_csorbanc_var"
        ));

        db.sightDao().insert(new Sight(
                "Festetics-kastély",
                "A keszthelyi Festetics-kastély Magyarország egyik leglátogatottabb történelmi épülete, több állandó kiállítással és rendezvénnyel.",
                "Keszthely",
                "sight_festetics_kastely_keszthely"
        ));

        db.sightDao().insert(new Sight(
                "Tapolcai-tavasbarlang",
                "A Tapolcai-tavasbarlang különlegessége, hogy csónakkal járható be a víz alatti üreg, és a látogatóközpontban izgalmas kiállítás is vár.",
                "Tapolca",
                "sight_tapolcai_tavasbarlang"
        ));
    }
}

