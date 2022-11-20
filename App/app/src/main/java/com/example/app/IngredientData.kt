package com.example.app

val s = "vegetable"

class IngredientData {

    val spinnerList = arrayListOf<String>(
        "쌀/곡식",
        "견과",
        "과일",
        "채소",
        "버섯",
        "육류",
        "유제품",
        "해산물",
        "소스",
        "양념",
        "허브",
        "통조림",
        "술"
    )

    fun getNameFromId(nameCode: String): String {
        if (nameCode.contains("processedfood_")) return processedFood[nameCode].toString()
        if (nameCode.contains("grain_")) return grain[nameCode].toString()
        if (nameCode.contains("nut_")) return nut[nameCode].toString()
        if (nameCode.contains("fruit_")) return fruit[nameCode].toString()
        if (nameCode.contains("citrus_")) return citrus[nameCode].toString()
        if (nameCode.contains("${s}_")) return vegatable[nameCode].toString()
        if (nameCode.contains("mushroom_")) return mushroom[nameCode].toString()
        if (nameCode.contains("meat_pork_")) return meat_pork[nameCode].toString()
        if (nameCode.contains("meat_beef_")) return meat_beef[nameCode].toString()
        if (nameCode.contains("meat_chicken_")) return meat_chicken[nameCode].toString()
        if (nameCode.contains("meat_lamb_")) return meat_lamb[nameCode].toString()
        if (nameCode.contains("meat_processed_")) return meat_processed[nameCode].toString()
        if (nameCode.contains("dairy_")) return dairy[nameCode].toString()
        if (nameCode.contains("seafood_")) return seafood[nameCode].toString()
        if (nameCode.contains("seafood_fish_")) return seafood_fish[nameCode].toString()
        if (nameCode.contains("sauce_")) return sauce[nameCode].toString()
        if (nameCode.contains("spice_")) return spice[nameCode].toString()
        if (nameCode.contains("herb_")) return herb[nameCode].toString()
        if (nameCode.contains("can_")) return can[nameCode].toString()
        if (nameCode.contains("wine_")) return wine[nameCode].toString()
        else return nameCode
    }

    fun getKeyName(value: String): String {
        for(i in dataList){
            if (i.value == value){
                return i.key
            }
        }
        return "no key"
    }

    //grain_
    val grain = hashMapOf(
        "grain_rice" to "쌀",                // 쌀
        "grain_corn" to "옥수수",                // 옥수수
        "grain_redbean" to "팥",                // 팥
        "grain_mungbean" to "녹두",            // 녹두
        "grain_sesame" to "참깨",
    )
    val spinnerGrain = (grain.values).toList()

    //nut_
    val nut = hashMapOf(
        "nut_acorn" to "도토리",                    // 도토리
        "nut_peanut" to "땅콩",                // 땅콩
        "nut_chestnut" to "밤",                // 밤
        "nut_almond" to "아몬드",                // 아몬드
        "nut_ginkgo" to "은행",                // 은행
        "nut_pinenut" to "잣",                // 잣
        "nut_walnut" to "호두",                // 호두
    )
    val spinnerNut = (nut.values).toList()

    //fruit_
    val fruit = hashMapOf(
        "fruit_strawberry" to "딸기",            // 딸기
        "fruit_cherry" to "체리",                // 체리
        "fruit_pear" to "배",                    // 배
        "fruit_apple" to "사과",                // 사과
        "fruit_orange" to "오렌지",                // 오렌지
        "fruit_watermelon" to "수박",            // 수박
        "fruit_melon" to "멜론",                // 멜론
        "fruit_koreanmelon" to "참외",            // 참외
        "fruit_grape" to "포도",                // 포도
        "fruit_persimmon" to "감",            // 감
        "fruit_pomegranate" to "석류",            // 석류
        "fruit_mango" to "망고",                // 망고
        "fruit_banana" to "바나나",                // 바나나
        "fruit_pineapple" to "파인애플",            // 파인애플
        "fruit_peach" to "복숭아",                // 복숭아
        "fruit_plum" to "자두",                // 자두
    )

    //citrus
    val citrus = hashMapOf(
        "citrus_mandarinorange" to "감귤",        // 감귤
        "citrus_orange" to "오렌지",                // 오렌지
        "citrus_lemon" to "레몬",                    // 레몬
        "citrus_lime" to "라임",                    // 라임
        "citrus_yuzu" to "유자",                    // 유자
    )
    val spinnerFruit = (fruit.values + citrus.values).toList()

    //vegatable

    val vegatable = hashMapOf(
        "${s}_cabbage" to "양배추",            // 양배추
        "${s}_kohlrabi" to "콜라비",            // 콜라비
        "${s}_cauliflower" to "콜리플라워",        // 콜리플라워
        "${s}_broccoli" to "브로콜리",            // 브로콜리
        "${s}_kale" to "케일",                // 케일
        "${s}_napacabbage" to "배추",        // 배추
        "${s}_daikon" to "무",                // 무
        "${s}_radish" to "순무",                // 순무
        "${s}_shepherdpurse" to "냉이",        // 냉이
        "${s}_bokchoi" to "청경채",            // 청경채
        "${s}_chilipepper" to "고추",        // 고추
        "${s}_bellpepper" to "피망",            // 피망
        "${s}_paprika" to "파프리카",            // 파프리카
        "${s}_peperoncino" to "페페론치노",        // 페페론치노
        "${s}_eggplant" to "가지",            // 가지
        "${s}_potato" to "감자",                // 감자
        "${s}_tomato" to "토마토",                // 토마토
        "${s}_zucchini" to "주키니",            // 주키니
        "${s}_youngpumpkin" to "애호박",        // 애호박
        "${s}_pumpkin" to "호박",            // 호박
        "${s}_kabocha" to "단호박",            // 단호박
        "${s}_cucumber" to "오이",            // 오이
        "${s}_spinach" to "시금치",            // 시금치
        "${s}_beet" to "비트",                // 비트
        "${s}_carrot" to "당근",                // 당근
        "${s}_javanica" to "미나리",            // 미나리
        "${s}_celery" to "샐러리",                // 샐러리
        "${s}_angelica" to "당귀",            // 당귀
        "${s}_parsley" to "파슬리",            // 파슬리
        "${s}_parsnip" to "파스닙",            // 파스닙
        "${s}_asparagus" to "아스파라거스",            // 아스파라거스
        "${s}_malva" to "아욱",                // 아욱
        "${s}_cichorium" to "치커리",            // 치커리
        "${s}_cirsium" to "곤드레",            // 곤드레
        "${s}_mugwort" to "쑥",            // 쑥
        "${s}_coronarium" to "쑥갓",            // 쑥갓
        "${s}_burdock" to "우엉",            // 우엉
        "${s}_ragwort" to "곰취",            // 곰취
        "${s}_lettuce" to "상추",            // 상추
        "${s}_headlettuce" to "양상추",        // 양상추
        "${s}_ballonflower" to "도라지",        // 도라지
        "${s}_deodeok" to "더덕",            // 더덕
        "${s}_bracken" to "고사리",            // 고사리
        "${s}_sweetpotato" to "고구마",        // 고구마
        "${s}_taro" to "토란",                // 토란
        "${s}_lotusrhizome" to "연근",        // 연근
        "${s}_bamboosprouts" to "죽순",        // 죽순
        "${s}_ginseng" to "인삼",            // 인삼
        "${s}_ginger" to "생강",                // 생강
        "${s}_sedum" to "돌나물",                // 돌나물
        "${s}_soybeansprout" to "콩나물",        // 콩나물
        "${s}_mungbeansprout" to "숙주나물",        // 숙주나물
        "${s}_onion" to "양파",                // 양파
        "${s}_shallot" to "샬럿",            // 샬럿
        "${s}_tuberosum" to "부추",            // 부추
        "${s}_scallion" to "대파",            // 대파
        "${s}_proliferum" to "쪽파",            // 쪽파
        "${s}_perillaleaf" to "깻잎",            // 깻잎
    )
    val spinnerVegetable = vegatable.values.toList()

    //mushroom
    val mushroom = hashMapOf(
        "mushroom_kingoyster" to "새송이버섯",            // 새송이버섯
        "mushroom_enoki" to "팽이버섯",                // 팽이버섯
        "mushroom_shiitake" to "표고버섯",            // 표고버섯
        "mushroom_woodear" to "목이버섯",                // 목이버섯
        "mushroom_champignon" to "양송이버섯",            // 양송이버섯
        "mushroom_oyster" to "느타리버섯",                // 느타리버섯
        "mushroom_sarcodon" to "능이버섯",            // 능이버섯
    )
    val spinnerMushroom = mushroom.values.toList()


    //meat_pork
    val meat_pork = hashMapOf(
        "meat_pork_head" to "편육",                // 편육
        "meat_pork_shoulder" to "목살",            // 목살
        "meat_pork_picnic" to "앞다리살",                // 앞다리살
        "meat_pork_hock" to "사태",                // 사태
        "meat_pork_rib" to "갈비",                // 갈비
        "meat_pork_skirt" to "등심덧살(가브리살)",                // 등심덧살(가브리살)
        "meat_pork_tenderloin" to "등심",            // 등심
        "meat_pork_belly" to "삼겹살",                // 삼겹살
        "meat_pork_ham" to "뒷다리살",                // 뒷다리살
        "meat_pork_feet" to "족발",                // 족발
        "meat_pork_jowl" to "항정살",                // 항정살
        "meat_pork_ground" to "간고기",                // 간고기
        "meat_pork_makchang" to "막창",            // 막창
        "meat_pork_gopchang" to "곱창",            // 곱창
        "meat_pork_skin" to "돼지껍데기",                // 돼지껍데기
        "meat_pork_skinbelly" to "오겹살",            // 오겹살
        "meat_pork_backmeat" to "뒷고기",            // 뒷고기
        "meat_pork_" to "",                    //
    )
    val key_meat_pork = meat_pork.keys

    //meat_beef
    val meat_beef = hashMapOf(
        "meat_beef_shoulder" to "목살",            // 목살
        "meat_beef_chuckeyeroll" to "척아이롤",        // 척아이롤
        "meat_beef_sirloin" to "등심",            // 등심
        "meat_beef_chuckflaptail" to "살치살",        // 살치살
        "meat_beef_ribcap" to "새우살",                // 새우살
        "meat_beef_chuckend" to "꽃등심",            // 꽃등심
        "meat_beef_ribeye" to "중간등심(꽃등심+아랫등심)",                // 중간등심(꽃등심+아랫등심)
        "meat_beef_tomahawk" to "토마호크",            // 토마호크
        "meat_beef_tenderloin" to "안심",            // 안심
        "meat_beef_strip" to "채끝살",                // 채끝살
        "meat_beef_oyster" to "부챗살",                // 부챗살
        "meat_beef_shank" to "사태",                // 사태
        "meat_beef_dogani" to "도가니",                // 도가니
        "meat_beef_rib" to "갈비",                // 갈비
        "meat_beef_larib" to "LA갈비",                // La갈비
        "meat_beef_shortrib" to "우대갈비",            // 우대갈비
        "meat_beef_fraldinha" to "안창살",            // 안창살
        "meat_beef_plate" to "양지",                // 양지
        "meat_beef_brisket" to "차돌박이",            // 차돌박이
        "meat_beef_beefloin" to "우삼겹(업진살)",            // 우삼겹(업진살)
        "meat_beef_flank" to "치마살",                // 치마살
        "meat_beef_topside" to "우둔살",            // 우둔살
        "meat_beef_butt" to "설도",                // 설도
        "meat_beef_gopchang" to "곱창",            // 곱창
        "meat_beef_daechang" to "대창",            // 대창
        "meat_beef_makchang" to "막창",            // 막창
        "meat_beef_" to "",            //
    )

    //meat_chicken
    val meat_chicken = hashMapOf(
        "meat_chicken_breast" to "닭가슴살",            // 닭가슴살
        "meat_chicken_wing" to "닭날개",            // 닭날개
        "meat_chicken_drumstick" to "닭다리",        // 닭다리
        "meat_chicken_egg" to "계란",                // 계란
        "meat_chicken_gizzard" to "닭똥집",            // 닭똥집
        "meat_chicken_thigh" to "닭정육(닭다리순살)",            // 닭정육(닭다리순살)
        "meat_chicken_bong" to "닭봉",            // 닭봉
        "meat_chicken_skin" to "닭껍질",            // 닭껍질
        "meat_chicken_whole" to "통닭",            // 통닭
        "meat_chicken_prep" to "손질된 닭",            // 손질된 닭
        "meat_chicken_egg" to "계란",                // 계란
        "meat_chicken_duck" to "오리",            // 오리
    )

    //meat_lamb
    val meat_lamb = hashMapOf(
        "meat_lamb_loin" to "양등심",                // 양등심
        "meat_lamb_rack" to "양갈비",                // 양갈비
        "meat_lamb_" to " ",        //
    )

    //meat_processed
    val meat_processed = hashMapOf(
        "meat_processed_bacon" to "베이컨",            // 베이컨
        "meat_processed_ham" to "햄",            // 햄
        "meat_processed_salami" to "살라미",        // 살라미
        "meat_processed_vienna" to "비엔나",        // 비엔나
        "meat_processed_beefjerky" to "육포",        // 육포
        "meat_processed_" to " ",        //
    )
    val spinnerMeat =
        (meat_processed.values + meat_lamb.values + meat_chicken.values + meat_pork.values + meat_beef.values).toList()

    //dairy
    val dairy = hashMapOf(
        "dairy_milk" to "우유",                    // 우유
        "dairy_creamcheese" to "크림치즈",            // 크림치즈
        "dairy_pizzacheese" to "피자치즈",            // 피자치즈
        "dairy_whippingcream" to "휘핑크림",            // 휘핑크림
        "dairy_dairycream" to "생크림",                // 생크림
        "dairy_sourcream" to "사워크림",                // 사워크림
        "dairy_" to "",                //우유
    )
    val spinnerDairy = dairy.values.toList()

    //seafood
    val seafood = hashMapOf(
        "seafood_squid" to "오징어",                // 오징어
        "seafood_octopus" to "문어",                // 문어
        "seafood_smalloctopus" to "낙지",        // 낙지
        "seafood_shrimp" to "새우",                // 새우
        "seafood_crab" to "꽃게",                // 꽃게
        "seafood_snowcrab" to "대게",            // 대게
        "seafood_cockle" to "꼬막",                // 꼬막
        "seafood_oyster" to "굴",                // 굴
        "seafood_clam" to "바지락",                // 바지락
        "seafood_abalone" to "전복",                // 전복
        "seafood_fishcake" to "어묵",            // 어묵
        "seafood_crabstick" to "게맛살",            // 게맛살
        "seafood_seaweed" to "김",                // 김
        "seafood_kelp" to "다시마",                // 다시마
        "seafood_seamustard" to "미역",            // 미역
        "seafood_mussel" to "홍합",                // 홍합
        "seafood_" to "",                    //
    )

    //seafood_fish
    val seafood_fish = hashMapOf(
        "seafood_fish_lepturus" to "갈치",        // 갈치
        "seafood_fish_scomber" to "고등어",        // 고등어
        "seafood_fish_saira" to "꽁치",            // 꽁치
        "seafood_fish_niphonius" to "삼치",        // 삼치
        "seafood_fish_azonus" to "임연수어",            // 임연수어
    )
    val spinnerSeafood = (seafood_fish.values + seafood.values).toList()

    //sauce
    val sauce = hashMapOf(
        "sauce_gochujang" to "고추장",                // 고추장
        "sauce_chogochujang" to "초고추장",            // 초고추장
        "sauce_ssamjang" to "쌈장",                // 쌈장
        "sauce_soybeanpaste" to "된장",            // 된장
        "sauce_cheonggukjang" to "청국장",            // 청국장
        "sauce_soysauce" to "간장",                // 간장
        "sauce_tsuyu" to "쯔유",                    // 쯔유
        "sauce_darksoysauce" to "노추",            // 노추
        "sauce_chunjang" to "춘장",                // 춘장
        "sauce_broadbeanchili" to "두반장",            // 두반장
        "sauce_oystersauce" to "굴소스",            // 굴소스
        "sauce_sugar" to "설탕",                    // 설탕
        "sauce_olligodang" to "올리고당",                // 올리고당
        "sauce_honey" to "꿀",                    // 꿀
        "sauce_peanutbutter" to "땅콩버터",            // 땅콩버터
        "sauce_maplesyrup" to "메이플시럽",                // 메이플시럽
        "sauce_mayo" to "마요네즈",                    // 마요네즈
        "sauce_mustard" to "머스타드",                // 머스타드
        "sauce_ketchup" to "케챱",                // 케챱
        "sauce_fishsauce" to "액젓",                // 액젓
        "sauce_sault" to "소금",                    // 소금
        "sauce_vinegar" to "식초",                // 식초
        "sauce_balsamic" to "발사믹 식초",                // 발사믹 식초
        "sauce_chickenstock" to "치킨스톡",            // 치킨스톡
        "sauce_beefstock" to "비프스톡",                // 비프스톡
        "sauce_cookingwine" to "맛술",            // 맛술
        "sauce_creamspaghetti" to "크림스파게티",            // 크림스파게티
        "sauce_tomatospaghetti" to "토마토스파게티",        // 토마토스파게티
        "sauce_rosespaghetti" to "로제스파게티",            // 로제스파게티
        "sauce_sesameoil" to "참기름",                // 참기름
        "sauce_perillaoil" to "들기름",                // 들기름
        "sauce_curry" to "카레",                    // 카레
        "sauce_jajang" to "짜장",                    // 짜장
    )
    val spinnerSauce = sauce.values.toList()

    //spice
    val spice = hashMapOf(
        "spice_galic" to "마늘",                    // 마늘
        "spice_staranise" to "팔각",                // 팔각
        "spice_clove" to "정향",                    // 정향
        "spice_wasabi" to "와사비",                    // 와사비
    )
    val spinnerSpice = spice.values.toList()

    val processedFood = hashMapOf(
        "processedfood_dumpling" to "만두",        // 만두
        "processedfood_koreameatball" to "동그랑땡",    // 동그랑땡
        "processedfood_meatball" to "미트볼",        // 미트볼
        "processedfood_koreansausage" to "순대",    // 순대
        "processedfood_tofu" to "두부",            // 두부
        "processedfood_silkentofu" to "순두부",        // 순두부
        "processedfood_driedtofu" to "건두부",        // 건두부
    )
    val spinnerProcess = processedFood.toList()
    //herb
    val herb = hashMapOf(
        "herb_mint" to "박하",                    // 박하
        "herb_rosemary" to "로즈마리",                // 로즈마리
        "herb_dill" to "딜",                    // 딜
        "herb_thyme" to "타임",                    // 타임
        "herb_basil" to "바질",                    // 바질
        "herb_oregano" to "오레가노",                    // 오레가노
        "herb_coriander" to "고수",                // 고수
        "herb_" to "",                //
    )
    val spinnerHerb = herb.values.toList()

    //can
    val can = hashMapOf(
        "can_corn" to "캔옥수수",                    // 캔옥수수
        "can_ham" to "캔햄",                        // 캔햄
        "can_chilituna" to "고추참치",                // 고추참치
        "can_tuna" to "참치",                        // 참치
        "can_vegetabletuna" to "야채참치",            // 야채참치
    )
    val spinnerCan = can.values.toList()

    //wine
    val wine = hashMapOf(
        "wine_white" to "화이트와인",                    // 화이트와인
        "wine_red" to "식초"                    // 식초
    )
    val spinnerWine = wine.values.toList()


    //전체 데이터
    val dataList = hashMapOf<String, String>(
        "grain_rice" to "쌀",                // 쌀
        "grain_corn" to "옥수수",                // 옥수수
        "grain_redbean" to "팥",                // 팥
        "grain_mungbean" to "녹두",            // 녹두
        "grain_sesame" to "참깨",                // 참깨
        "grain_starch" to "전분",
        "nut_acorn" to "도토리",                    // 도토리
        "nut_peanut" to "땅콩",                // 땅콩
        "nut_chestnut" to "밤",                // 밤
        "nut_almond" to "아몬드",                // 아몬드
        "nut_ginkgo" to "은행",                // 은행
        "nut_pinenut" to "잣",                // 잣
        "nut_walnut" to "호두",                // 호두

        "fruit_strawberry" to "딸기",            // 딸기
        "fruit_cherry" to "체리",                // 체리
        "fruit_pear" to "배",                    // 배
        "fruit_apple" to "사과",                // 사과
        "fruit_orange" to "오렌지",                // 오렌지
        "fruit_watermelon" to "수박",            // 수박
        "fruit_melon" to "멜론",                // 멜론
        "fruit_koreanmelon" to "참외",            // 참외
        "fruit_grape" to "포도",                // 포도
        "fruit_persimmon" to "감",            // 감
        "fruit_pomegranate" to "석류",            // 석류
        "fruit_mango" to "망고",                // 망고
        "fruit_banana" to "바나나",                // 바나나
        "fruit_pineapple" to "파인애플",            // 파인애플
        "fruit_peach" to "복숭아",                // 복숭아
        "fruit_plum" to "자두",                // 자두

        "citrus_mandarinorange" to "감귤",        // 감귤
        "citrus_orange" to "오렌지",                // 오렌지
        "citrus_lemon" to "레몬",                    // 레몬
        "citrus_lime" to "라임",                    // 라임
        "citrus_yuzu" to "유자",                    // 유자

        "${s}_cabbage" to "양배추",            // 양배추
        "${s}_kohlrabi" to "콜라비",            // 콜라비
        "${s}_cauliflower" to "콜리플라워",        // 콜리플라워
        "${s}_broccoli" to "브로콜리",            // 브로콜리
        "${s}_kale" to "케일",                // 케일
        "${s}_napacabbage" to "배추",        // 배추
        "${s}_daikon" to "무",                // 무
        "${s}_radish" to "순무",                // 순무
        "${s}_shepherdpurse" to "냉이",        // 냉이
        "${s}_bokchoi" to "청경채",            // 청경채
        "${s}_chilipepper" to "고추",        // 고추
        "${s}_bellpepper" to "피망",            // 피망
        "${s}_paprika" to "파프리카",            // 파프리카
        "${s}_peperoncino" to "페페론치노",        // 페페론치노
        "${s}_eggplant" to "가지",            // 가지
        "${s}_potato" to "감자",                // 감자
        "${s}_tomato" to "토마토",                // 토마토
        "${s}_zucchini" to "주키니",            // 주키니
        "${s}_youngpumpkin" to "애호박",        // 애호박
        "${s}_pumpkin" to "호박",            // 호박
        "${s}_kabocha" to "단호박",            // 단호박
        "${s}_cucumber" to "오이",            // 오이
        "${s}_spinach" to "시금치",            // 시금치
        "${s}_beet" to "비트",                // 비트
        "${s}_carrot" to "당근",                // 당근
        "${s}_javanica" to "미나리",            // 미나리
        "${s}_celery" to "샐러리",                // 샐러리
        "${s}_angelica" to "당귀",            // 당귀
        "${s}_parsley" to "파슬리",            // 파슬리
        "${s}_parsnip" to "파스닙",            // 파스닙
        "${s}_asparagus" to "아스파라거스",            // 아스파라거스
        "${s}_malva" to "아욱",                // 아욱
        "${s}_cichorium" to "치커리",            // 치커리
        "${s}_cirsium" to "곤드레",            // 곤드레
        "${s}_mugwort" to "쑥",            // 쑥
        "${s}_coronarium" to "쑥갓",            // 쑥갓
        "${s}_burdock" to "우엉",            // 우엉
        "${s}_ragwort" to "곰취",            // 곰취
        "${s}_lettuce" to "상추",            // 상추
        "${s}_headlettuce" to "양상추",        // 양상추
        "${s}_ballonflower" to "도라지",        // 도라지
        "${s}_deodeok" to "더덕",            // 더덕
        "${s}_bracken" to "고사리",            // 고사리
        "${s}_sweetpotato" to "고구마",        // 고구마
        "${s}_taro" to "토란",                // 토란
        "${s}_lotusrhizome" to "연근",        // 연근
        "${s}_bamboosprouts" to "죽순",        // 죽순
        "${s}_ginseng" to "인삼",            // 인삼
        "${s}_ginger" to "생강",                // 생강
        "${s}_sedum" to "돌나물",                // 돌나물
        "${s}_soybeansprout" to "콩나물",        // 콩나물
        "${s}_mungbeansprout" to "숙주나물",        // 숙주나물
        "${s}_onion" to "양파",                // 양파
        "${s}_shallot" to "샬럿",            // 샬럿
        "${s}_tuberosum" to "부추",            // 부추
        "${s}_scallion" to "대파",            // 대파
        "${s}_proliferum" to "쪽파",            // 쪽파
        "${s}_perillaleaf" to "깻잎",            // 깻잎
        "${s}_" to "",        //

        "mushroom_kingoyster" to "새송이버섯",            // 새송이버섯
        "mushroom_enoki" to "팽이버섯",                // 팽이버섯
        "mushroom_shiitake" to "표고버섯",            // 표고버섯
        "mushroom_woodear" to "목이버섯",                // 목이버섯
        "mushroom_champignon" to "양송이버섯",            // 양송이버섯
        "mushroom_oyster" to "느타리버섯",                // 느타리버섯
        "mushroom_sarcodon" to "능이버섯",            // 능이버섯

        "meat_pork_head" to "편육",                // 편육
        "meat_pork_shoulder" to "목살",            // 목살
        "meat_pork_picnic" to "앞다리살",                // 앞다리살
        "meat_pork_hock" to "사태",                // 사태
        "meat_pork_rib" to "갈비",                // 갈비
        "meat_pork_skirt" to "등심덧살(가브리살)",                // 등심덧살(가브리살)
        "meat_pork_tenderloin" to "등심",            // 등심
        "meat_pork_belly" to "삼겹살",                // 삼겹살
        "meat_pork_ham" to "뒷다리살",                // 뒷다리살
        "meat_pork_feet" to "족발",                // 족발
        "meat_pork_jowl" to "항정살",                // 항정살
        "meat_pork_ground" to "간고기",                // 간고기
        "meat_pork_makchang" to "막창",            // 막창
        "meat_pork_gopchang" to "곱창",            // 곱창
        "meat_pork_skin" to "돼지껍데기",                // 돼지껍데기
        "meat_pork_skinbelly" to "오겹살",            // 오겹살
        "meat_pork_backmeat" to "뒷고기",            // 뒷고기
        "meat_pork_" to "",                    //

        "meat_beef_shoulder" to "목살",            // 목살
        "meat_beef_chuckeyeroll" to "척아이롤",        // 척아이롤
        "meat_beef_sirloin" to "등심",            // 등심
        "meat_beef_chuckflaptail" to "살치살",        // 살치살
        "meat_beef_ribcap" to "새우살",                // 새우살
        "meat_beef_chuckend" to "꽃등심",            // 꽃등심
        "meat_beef_ribeye" to "중간등심(꽃등심+아랫등심)",                // 중간등심(꽃등심+아랫등심)
        "meat_beef_tomahawk" to "토마호크",            // 토마호크
        "meat_beef_tenderloin" to "안심",            // 안심
        "meat_beef_strip" to "채끝살",                // 채끝살
        "meat_beef_oyster" to "부챗살",                // 부챗살
        "meat_beef_shank" to "사태",                // 사태
        "meat_beef_dogani" to "도가니",                // 도가니
        "meat_beef_rib" to "갈비",                // 갈비
        "meat_beef_larib" to "LA갈비",                // La갈비
        "meat_beef_shortrib" to "우대갈비",            // 우대갈비
        "meat_beef_fraldinha" to "안창살",            // 안창살
        "meat_beef_plate" to "양지",                // 양지
        "meat_beef_brisket" to "차돌박이",            // 차돌박이
        "meat_beef_beefloin" to "우삼겹(업진살)",            // 우삼겹(업진살)
        "meat_beef_flank" to "치마살",                // 치마살
        "meat_beef_topside" to "우둔살",            // 우둔살
        "meat_beef_butt" to "설도",                // 설도
        "meat_beef_gopchang" to "곱창",            // 곱창
        "meat_beef_daechang" to "대창",            // 대창
        "meat_beef_makchang" to "막창",            // 막창
        "meat_beef_" to "",            //

        "meat_chicken_breast" to "닭가슴살",            // 닭가슴살
        "meat_chicken_wing" to "닭날개",            // 닭날개
        "meat_chicken_drumstick" to "닭다리",        // 닭다리
        "meat_chicken_egg" to "계란",                // 계란
        "meat_chicken_gizzard" to "닭똥집",            // 닭똥집
        "meat_chicken_thigh" to "닭정육(닭다리순살)",            // 닭정육(닭다리순살)
        "meat_chicken_bong" to "닭봉",            // 닭봉
        "meat_chicken_skin" to "닭껍질",            // 닭껍질
        "meat_chicken_whole" to "통닭",            // 통닭
        "meat_chicken_prep" to "손질된 닭",            // 손질된 닭
        "meat_chicken_egg" to "계란",                // 계란
        "meat_chicken_duck" to "오리",            // 오리

        "meat_lamb_loin" to "양등심",                // 양등심
        "meat_lamb_rack" to "양갈비",                // 양갈비
        "meat_lamb_" to " ",        //

        "meat_processed_bacon" to "베이컨",            // 베이컨
        "meat_processed_ham" to "햄",            // 햄
        "meat_processed_salami" to "살라미",        // 살라미
        "meat_processed_vienna" to "비엔나",        // 비엔나
        "meat_processed_beefjerky" to "육포",        // 육포
        "meat_processed_" to " ",        //

        "dairy_milk" to "우유",                    // 우유
        "dairy_creamcheese" to "크림치즈",            // 크림치즈
        "dairy_pizzacheese" to "피자치즈",            // 피자치즈
        "dairy_whippingcream" to "휘핑크림",            // 휘핑크림
        "dairy_dairycream" to "생크림",                // 생크림
        "dairy_sourcream" to "사워크림",                // 사워크림
        "dairy_" to "",                //우유

        "seafood_fish_lepturus" to "갈치",        // 갈치
        "seafood_fish_scomber" to "고등어",        // 고등어
        "seafood_fish_saira" to "꽁치",            // 꽁치
        "seafood_fish_niphonius" to "삼치",        // 삼치
        "seafood_fish_azonus" to "임연수어",            // 임연수어
        "seafood_squid" to "오징어",                // 오징어
        "seafood_octopus" to "문어",                // 문어
        "seafood_smalloctopus" to "낙지",        // 낙지
        "seafood_shrimp" to "새우",                // 새우
        "seafood_crab" to "꽃게",                // 꽃게
        "seafood_snowcrab" to "대게",            // 대게
        "seafood_cockle" to "꼬막",                // 꼬막
        "seafood_oyster" to "굴",                // 굴
        "seafood_clam" to "바지락",                // 바지락
        "seafood_abalone" to "전복",                // 전복
        "seafood_fishcake" to "어묵",            // 어묵
        "seafood_crabstick" to "게맛살",            // 게맛살
        "seafood_seaweed" to "김",                // 김
        "seafood_kelp" to "다시마",                // 다시마
        "seafood_seamustard" to "미역",            // 미역
        "seafood_mussel" to "홍합",                // 홍합
        "seafood_" to "",                    //

        "processedfood_dumpling" to "만두",        // 만두
        "processedfood_koreameatball" to "동그랑땡",    // 동그랑땡
        "processedfood_meatball" to "미트볼",        // 미트볼
        "processedfood_koreansausage" to "순대",    // 순대
        "processedfood_tofu" to "두부",            // 두부
        "processedfood_silkentofu" to "순두부",        // 순두부
        "processedfood_driedtofu" to "건두부",        // 건두부

        "sauce_msg" to "MSG",
        "sauce_msgsault" to "맛소금",
        "sauce_gochujang" to "고추장",                // 고추장
        "sauce_chogochujang" to "초고추장",            // 초고추장
        "sauce_ssamjang" to "쌈장",                // 쌈장
        "sauce_soybeanpaste" to "된장",            // 된장
        "sauce_cheonggukjang" to "청국장",            // 청국장
        "sauce_soysauce" to "간장",                // 간장
        "sauce_tsuyu" to "쯔유",                    // 쯔유
        "sauce_darksoysauce" to "노추",            // 노추
        "sauce_chunjang" to "춘장",                // 춘장
        "sauce_broadbeanchili" to "두반장",            // 두반장
        "sauce_oystersauce" to "굴소스",            // 굴소스
        "sauce_sugar" to "설탕",                    // 설탕
        "sauce_olligodang" to "올리고당",                // 올리고당
        "sauce_honey" to "꿀",                    // 꿀
        "sauce_peanutbutter" to "땅콩버터",            // 땅콩버터
        "sauce_maplesyrup" to "메이플시럽",                // 메이플시럽
        "sauce_mayo" to "마요네즈",                    // 마요네즈
        "sauce_mustard" to "머스타드",                // 머스타드
        "sauce_ketchup" to "케챱",                // 케챱
        "sauce_fishsauce" to "액젓",                // 액젓
        "sauce_sault" to "소금",                    // 소금
        "sauce_vinegar" to "식초",                // 식초
        "sauce_balsamic" to "발사믹 식초",                // 발사믹 식초
        "sauce_chickenstock" to "치킨스톡",            // 치킨스톡
        "sauce_beefstock" to "비프스톡",                // 비프스톡
        "sauce_cookingwine" to "맛술",            // 맛술
        "sauce_creamspaghetti" to "크림스파게티",            // 크림스파게티
        "sauce_tomatospaghetti" to "토마토스파게티",        // 토마토스파게티
        "sauce_rosespaghetti" to "로제스파게티",            // 로제스파게티
        "sauce_sesameoil" to "참기름",                // 참기름
        "sauce_perillaoil" to "들기름",                // 들기름
        "sauce_curry" to "카레",                    // 카레
        "sauce_jajang" to "짜장",                    // 짜장

        "spice_galic" to "마늘",                    // 마늘
        "spice_staranise" to "팔각",                // 팔각
        "spice_clove" to "정향",                    // 정향
        "spice_wasabi" to "와사비",                    // 와사비

        "herb_mint" to "박하",                    // 박하
        "herb_rosemary" to "로즈마리",                // 로즈마리
        "herb_dill" to "딜",                    // 딜
        "herb_thyme" to "타임",                    // 타임
        "herb_basil" to "바질",                    // 바질
        "herb_oregano" to "오레가노",                    // 오레가노
        "herb_coriander" to "고수",                // 고수
        "herb_" to "",                //

        "can_corn" to "캔옥수수",                    // 캔옥수수
        "can_ham" to "캔햄",                        // 캔햄
        "can_chilituna" to "고추참치",                // 고추참치
        "can_tuna" to "참치",                        // 참치
        "can_vegetabletuna" to "야채참치",            // 야채참치

        "wine_white" to "화이트와인",                    // 화이트와인
        "wine_red" to "식초"                    // 식초
    )
}