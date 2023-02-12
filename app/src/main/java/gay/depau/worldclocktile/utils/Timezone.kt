package gay.depau.worldclocktile.utils

// SPDX-License-Identifier: Apache-2.0
// This file is part of World Clock Tile.

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*

fun currentTimeAt(timezoneId: String): LocalDateTime {
    val timezone = TimeZone.getTimeZone(timezoneId)
    return LocalDateTime.now(timezone.toZoneId())
}

val LocalDateTime.reducedPrecision: LocalDateTime
    get() = LocalDateTime.of(year, month, dayOfMonth, hour, minute)

fun timezoneOffsetDescription(
    timezoneId: String,
    minutesOffset: Int? = null,
    hostile: Boolean = false
): String {
    if (hostile)
        return "-2 centuries"

    val localTimezone = TimeZone.getDefault()
    val otherTimezone = TimeZone.getTimeZone(timezoneId)

    var timeUTC = currentTimeAt("UTC").atZone(ZoneId.of("UTC"))

    if (minutesOffset != null)
        timeUTC = timeUTC.plusMinutes(minutesOffset.toLong())

    // Calculate time between the local and the other timezone
    val localTime = timeUTC.withZoneSameInstant(localTimezone.toZoneId())
    val otherTime = timeUTC.withZoneSameInstant(otherTimezone.toZoneId())

    val diff =
        (otherTime.toLocalDateTime().toEpochSecond(ZoneOffset.UTC) - localTime.toLocalDateTime()
            .toEpochSecond(ZoneOffset.UTC)).toInt()

    val hoursDiff = diff / 3600
    val minutesDiff = (diff % 3600) / 60

    if (diff == 0) return "Local time"

    val result = StringBuilder()

    if (diff > 0)
        result.append("+")

    if (hoursDiff != 0) result.append("${hoursDiff}h")

    if (minutesDiff != 0) {
        if (hoursDiff != 0) result.append(" ")
        result.append(minutesDiff).append("m")
    }

    // Check if the date in the other timezone is tomorrow or yesterday of the local date
    val localDate = localTime.toLocalDate()
    val otherDate = otherTime.toLocalDate()

    if (localDate.isBefore(otherDate)) result.append(" Tomorrow")
    else if (localDate.isAfter(otherDate)) result.append(" Yesterday")

    return result.toString()
}

val timezoneSimpleNames = mapOf(
    "Africa/Abidjan" to "Greenwich Mean Time",
    "Africa/Accra" to "Greenwich Mean Time",
    "Africa/Addis_Ababa" to "East Africa Time",
    "Africa/Algiers" to "Central European Time",
    "Africa/Asmara" to "East Africa Time",
    "Africa/Bamako" to "Greenwich Mean Time",
    "Africa/Bangui" to "West Africa Time",
    "Africa/Banjul" to "Greenwich Mean Time",
    "Africa/Bissau" to "Greenwich Mean Time",
    "Africa/Blantyre" to "Central Africa Time",
    "Africa/Brazzaville" to "West Africa Time",
    "Africa/Bujumbura" to "Central Africa Time",
    "Africa/Cairo" to "Eastern European Time",
    "Africa/Casablanca" to "Western European Time",
    "Africa/Conakry" to "Greenwich Mean Time",
    "Africa/Dakar" to "Greenwich Mean Time",
    "Africa/Dar_es_Salaam" to "East Africa Time",
    "Africa/Djibouti" to "East Africa Time",
    "Africa/Douala" to "West Africa Time",
    "Africa/El_Aaiun" to "Western European Time",
    "Africa/Freetown" to "Greenwich Mean Time",
    "Africa/Gaborone" to "Central Africa Time",
    "Africa/Harare" to "Central Africa Time",
    "Africa/Johannesburg" to "South Africa Time",
    "Africa/Juba" to "Central Africa Time",
    "Africa/Kampala" to "East Africa Time",
    "Africa/Khartoum" to "Central Africa Time",
    "Africa/Kigali" to "Central Africa Time",
    "Africa/Kinshasa" to "West Africa Time",
    "Africa/Lagos" to "West Africa Time",
    "Africa/Libreville" to "West Africa Time",
    "Africa/Lome" to "Greenwich Mean Time",
    "Africa/Luanda" to "West Africa Time",
    "Africa/Lubumbashi" to "Central Africa Time",
    "Africa/Lusaka" to "Central Africa Time",
    "Africa/Malabo" to "West Africa Time",
    "Africa/Maputo" to "Central Africa Time",
    "Africa/Maseru" to "South Africa Time",
    "Africa/Mbabane" to "South Africa Time",
    "Africa/Mogadishu" to "East Africa Time",
    "Africa/Monrovia" to "Greenwich Mean Time",
    "Africa/Nairobi" to "East Africa Time",
    "Africa/Ndjamena" to "West Africa Time",
    "Africa/Niamey" to "West Africa Time",
    "Africa/Nouakchott" to "Greenwich Mean Time",
    "Africa/Ouagadougou" to "Greenwich Mean Time",
    "Africa/Porto-Novo" to "West Africa Time",
    "Africa/Sao_Tome" to "Greenwich Mean Time",
    "Africa/Tripoli" to "Eastern European Time",
    "Africa/Tunis" to "Central European Time",
    "Africa/Windhoek" to "Central Africa Time",
    "America/Adak" to "Hawaii-Aleutian Time",
    "America/Anchorage" to "Alaska Time",
    "America/Anguilla" to "Atlantic Time",
    "America/Antigua" to "Atlantic Time",
    "America/Argentina/Buenos_Aires" to "Argentina Time",
    "America/Aruba" to "Atlantic Time",
    "America/Asuncion" to "Paraguay Time",
    "America/Atikokan" to "Eastern Time",
    "America/Barbados" to "Atlantic Time",
    "America/Belize" to "Central Time",
    "America/Blanc-Sablon" to "Atlantic Time",
    "America/Bogota" to "Colombia Time",
    "America/Cancun" to "Eastern Time",
    "America/Caracas" to "Venezuela Time",
    "America/Cayenne" to "French Guiana Time",
    "America/Cayman" to "Eastern Time",
    "America/Chicago" to "Central Time",
    "America/Costa_Rica" to "Central Time",
    "America/Curacao" to "Atlantic Time",
    "America/Danmarkshavn" to "Greenwich Mean Time",
    "America/Denver" to "Mountain Time",
    "America/Dominica" to "Atlantic Time",
    "America/Edmonton" to "Mountain Time",
    "America/El_Salvador" to "Central Time",
    "America/Grand_Turk" to "Eastern Time",
    "America/Grenada" to "Atlantic Time",
    "America/Guadeloupe" to "Atlantic Time",
    "America/Guatemala" to "Central Time",
    "America/Guayaquil" to "Ecuador Time",
    "America/Guyana" to "Guyana Time",
    "America/Halifax" to "Atlantic Time",
    "America/Havana" to "Cuba Time",
    "America/Hermosillo" to "Mexican Pacific Time",
    "America/Jamaica" to "Eastern Time",
    "America/Kralendijk" to "Atlantic Time",
    "America/La_Paz" to "Bolivia Time",
    "America/Lima" to "Peru Time",
    "America/Los_Angeles" to "Pacific Time",
    "America/Lower_Princes" to "Atlantic Time",
    "America/Managua" to "Central Time",
    "America/Manaus" to "Amazon Time",
    "America/Marigot" to "Atlantic Time",
    "America/Martinique" to "Atlantic Time",
    "America/Matamoros" to "Central Time",
    "America/Mexico_City" to "Central Time",
    "America/Miquelon" to "St. Pierre & Miquelon Time",
    "America/Montevideo" to "Uruguay Time",
    "America/Montserrat" to "Atlantic Time",
    "America/Nassau" to "Eastern Time",
    "America/New_York" to "Eastern Time",
    "America/Noronha" to "Fernando de Noronha Time",
    "America/Nuuk" to "West Greenland Time",
    "America/Panama" to "Eastern Time",
    "America/Paramaribo" to "Suriname Time",
    "America/Phoenix" to "Mountain Time",
    "America/Port-au-Prince" to "Eastern Time",
    "America/Port_of_Spain" to "Atlantic Time",
    "America/Puerto_Rico" to "Atlantic Time",
    "America/Punta_Arenas" to "Chile Time",
    "America/Regina" to "Central Time",
    "America/Rio_Branco" to "Acre Time",
    "America/Santiago" to "Chile Time",
    "America/Santo_Domingo" to "Atlantic Time",
    "America/Sao_Paulo" to "Brasilia Time",
    "America/Scoresbysund" to "East Greenland Time",
    "America/St_Barthelemy" to "Atlantic Time",
    "America/St_Johns" to "Newfoundland Time",
    "America/St_Kitts" to "Atlantic Time",
    "America/St_Lucia" to "Atlantic Time",
    "America/St_Thomas" to "Atlantic Time",
    "America/St_Vincent" to "Atlantic Time",
    "America/Tegucigalpa" to "Central Time",
    "America/Thule" to "Atlantic Time",
    "America/Tijuana" to "Pacific Time",
    "America/Toronto" to "Eastern Time",
    "America/Tortola" to "Atlantic Time",
    "America/Vancouver" to "Pacific Time",
    "America/Whitehorse" to "Yukon Time",
    "America/Winnipeg" to "Central Time",
    "Antarctica/Casey" to "Casey Time",
    "Antarctica/Davis" to "Davis Time",
    "Antarctica/DumontDUrville" to "Dumont-d’Urville Time",
    "Antarctica/Mawson" to "Mawson Time",
    "Antarctica/McMurdo" to "New Zealand Time",
    "Antarctica/Palmer" to "Chile Time",
    "Antarctica/Syowa" to "Syowa Time",
    "Antarctica/Troll" to "Greenwich Mean Time",
    "Antarctica/Vostok" to "Vostok Time",
    "Arctic/Longyearbyen" to "Central European Time",
    "Asia/Aden" to "Arabian Time",
    "Asia/Almaty" to "East Kazakhstan Time",
    "Asia/Amman" to "Asia/Amman",
    "Asia/Anadyr" to "Anadyr Time",
    "Asia/Aqtobe" to "West Kazakhstan Time",
    "Asia/Ashgabat" to "Turkmenistan Time",
    "Asia/Baghdad" to "Arabian Time",
    "Asia/Bahrain" to "Arabian Time",
    "Asia/Baku" to "Azerbaijan Time",
    "Asia/Bangkok" to "Indochina Time",
    "Asia/Beirut" to "Eastern European Time",
    "Asia/Bishkek" to "Kyrgyzstan Time",
    "Asia/Brunei" to "Brunei Darussalam Time",
    "Asia/Chita" to "Yakutsk Time",
    "Asia/Colombo" to "India Time",
    "Asia/Damascus" to "Asia/Damascus",
    "Asia/Dhaka" to "Bangladesh Time",
    "Asia/Dili" to "East Timor Time",
    "Asia/Dubai" to "Gulf Time",
    "Asia/Dushanbe" to "Tajikistan Time",
    "Asia/Hebron" to "Eastern European Time",
    "Asia/Ho_Chi_Minh" to "Indochina Time",
    "Asia/Hong_Kong" to "Hong Kong Time",
    "Asia/Hovd" to "Hovd Time",
    "Asia/Irkutsk" to "Irkutsk Time",
    "Asia/Jakarta" to "Western Indonesia Time",
    "Asia/Jayapura" to "Eastern Indonesia Time",
    "Asia/Jerusalem" to "Israel Time",
    "Asia/Kabul" to "Afghanistan Time",
    "Asia/Kamchatka" to "Petropavlovsk-Kamchatski Time",
    "Asia/Karachi" to "Pakistan Time",
    "Asia/Kathmandu" to "Nepal Time",
    "Asia/Kolkata" to "India Time",
    "Asia/Kuala_Lumpur" to "Malaysia Time",
    "Asia/Kuwait" to "Arabian Time",
    "Asia/Macau" to "China Time",
    "Asia/Makassar" to "Central Indonesia Time",
    "Asia/Manila" to "Philippine Time",
    "Asia/Muscat" to "Gulf Time",
    "Asia/Nicosia" to "Eastern European Time",
    "Asia/Novosibirsk" to "Novosibirsk Time",
    "Asia/Omsk" to "Omsk Time",
    "Asia/Phnom_Penh" to "Indochina Time",
    "Asia/Pyongyang" to "Korean Time",
    "Asia/Qatar" to "Arabian Time",
    "Asia/Riyadh" to "Arabian Time",
    "Asia/Sakhalin" to "Sakhalin Time",
    "Asia/Seoul" to "Korean Time",
    "Asia/Shanghai" to "China Time",
    "Asia/Singapore" to "Singapore Time",
    "Asia/Taipei" to "Taipei Time",
    "Asia/Tashkent" to "Uzbekistan Time",
    "Asia/Tbilisi" to "Georgia Time",
    "Asia/Tehran" to "Iran Time",
    "Asia/Thimphu" to "Bhutan Time",
    "Asia/Tokyo" to "Japan Time",
    "Asia/Ulaanbaatar" to "Ulaanbaatar Time",
    "Asia/Urumqi" to "China Time",
    "Asia/Vientiane" to "Indochina Time",
    "Asia/Vladivostok" to "Vladivostok Time",
    "Asia/Yangon" to "Myanmar Time",
    "Asia/Yekaterinburg" to "Yekaterinburg Time",
    "Asia/Yerevan" to "Armenia Time",
    "Atlantic/Azores" to "Azores Time",
    "Atlantic/Bermuda" to "Atlantic Time",
    "Atlantic/Canary" to "Western European Time",
    "Atlantic/Cape_Verde" to "Cape Verde Time",
    "Atlantic/Faroe" to "Western European Time",
    "Atlantic/Reykjavik" to "Greenwich Mean Time",
    "Atlantic/South_Georgia" to "South Georgia Time",
    "Atlantic/St_Helena" to "Greenwich Mean Time",
    "Atlantic/Stanley" to "Falkland Islands Time",
    "Australia/Adelaide" to "Australian Central Time",
    "Australia/Brisbane" to "Australian Eastern Time",
    "Australia/Darwin" to "Australian Central Time",
    "Australia/Eucla" to "Australian Central Western Time",
    "Australia/Lord_Howe" to "Lord Howe Time",
    "Australia/Perth" to "Australian Western Time",
    "Australia/Sydney" to "Australian Eastern Time",
    "Europe/Amsterdam" to "Central European Time",
    "Europe/Andorra" to "Central European Time",
    "Europe/Athens" to "Eastern European Time",
    "Europe/Belgrade" to "Central European Time",
    "Europe/Berlin" to "Central European Time",
    "Europe/Bratislava" to "Central European Time",
    "Europe/Brussels" to "Central European Time",
    "Europe/Bucharest" to "Eastern European Time",
    "Europe/Budapest" to "Central European Time",
    "Europe/Chisinau" to "Eastern European Time",
    "Europe/Copenhagen" to "Central European Time",
    "Europe/Dublin" to "Greenwich Mean Time",
    "Europe/Gibraltar" to "Central European Time",
    "Europe/Guernsey" to "Greenwich Mean Time",
    "Europe/Helsinki" to "Eastern European Time",
    "Europe/Isle_of_Man" to "Greenwich Mean Time",
    "Europe/Istanbul" to "Turkey Time",
    "Europe/Jersey" to "Greenwich Mean Time",
    "Europe/Kaliningrad" to "Eastern European Time",
    "Europe/Kiev" to "Eastern European Time",
    "Europe/Lisbon" to "Western European Time",
    "Europe/Ljubljana" to "Central European Time",
    "Europe/London" to "Greenwich Mean Time",
    "Europe/Luxembourg" to "Central European Time",
    "Europe/Madrid" to "Central European Time",
    "Europe/Malta" to "Central European Time",
    "Europe/Mariehamn" to "Eastern European Time",
    "Europe/Minsk" to "Moscow Time",
    "Europe/Monaco" to "Central European Time",
    "Europe/Moscow" to "Moscow Time",
    "Europe/Oslo" to "Central European Time",
    "Europe/Paris" to "Central European Time",
    "Europe/Podgorica" to "Central European Time",
    "Europe/Prague" to "Central European Time",
    "Europe/Riga" to "Eastern European Time",
    "Europe/Rome" to "Central European Time",
    "Europe/Samara" to "Samara Time",
    "Europe/San_Marino" to "Central European Time",
    "Europe/Sarajevo" to "Central European Time",
    "Europe/Simferopol" to "Moscow Time",
    "Europe/Skopje" to "Central European Time",
    "Europe/Sofia" to "Eastern European Time",
    "Europe/Stockholm" to "Central European Time",
    "Europe/Tallinn" to "Eastern European Time",
    "Europe/Tirane" to "Central European Time",
    "Europe/Vaduz" to "Central European Time",
    "Europe/Vatican" to "Central European Time",
    "Europe/Vienna" to "Central European Time",
    "Europe/Vilnius" to "Eastern European Time",
    "Europe/Warsaw" to "Central European Time",
    "Europe/Zagreb" to "Central European Time",
    "Europe/Zurich" to "Central European Time",
    "Indian/Antananarivo" to "East Africa Time",
    "Indian/Chagos" to "Indian Ocean Time",
    "Indian/Christmas" to "Christmas Island Time",
    "Indian/Cocos" to "Cocos Islands Time",
    "Indian/Comoro" to "East Africa Time",
    "Indian/Kerguelen" to "French Southern & Antarctic Time",
    "Indian/Mahe" to "Seychelles Time",
    "Indian/Maldives" to "Maldives Time",
    "Indian/Mauritius" to "Mauritius Time",
    "Indian/Mayotte" to "East Africa Time",
    "Indian/Reunion" to "Réunion Time",
    "Pacific/Apia" to "Apia Time",
    "Pacific/Auckland" to "New Zealand Time",
    "Pacific/Bougainville" to "Bougainville Time",
    "Pacific/Chatham" to "Chatham Time",
    "Pacific/Chuuk" to "Chuuk Time",
    "Pacific/Easter" to "Easter Island Time",
    "Pacific/Efate" to "Vanuatu Time",
    "Pacific/Fakaofo" to "Tokelau Time",
    "Pacific/Fiji" to "Fiji Time",
    "Pacific/Funafuti" to "Tuvalu Time",
    "Pacific/Galapagos" to "Galapagos Time",
    "Pacific/Gambier" to "Gambier Time",
    "Pacific/Guadalcanal" to "Solomon Islands Time",
    "Pacific/Guam" to "Chamorro Time",
    "Pacific/Honolulu" to "Hawaii-Aleutian Time",
    "Pacific/Kanton" to "Phoenix Islands Time",
    "Pacific/Kiritimati" to "Line Islands Time",
    "Pacific/Kosrae" to "Kosrae Time",
    "Pacific/Majuro" to "Marshall Islands Time",
    "Pacific/Marquesas" to "Marquesas Time",
    "Pacific/Midway" to "Samoa Time",
    "Pacific/Nauru" to "Nauru Time",
    "Pacific/Niue" to "Niue Time",
    "Pacific/Norfolk" to "Norfolk Island Time",
    "Pacific/Noumea" to "New Caledonia Time",
    "Pacific/Pago_Pago" to "Samoa Time",
    "Pacific/Palau" to "Palau Time",
    "Pacific/Pitcairn" to "Pitcairn Time",
    "Pacific/Port_Moresby" to "Papua New Guinea Time",
    "Pacific/Rarotonga" to "Cook Islands Time",
    "Pacific/Saipan" to "Chamorro Time",
    "Pacific/Tahiti" to "Tahiti Time",
    "Pacific/Tarawa" to "Gilbert Islands Time",
    "Pacific/Tongatapu" to "Tonga Time",
    "Pacific/Wake" to "Wake Island Time",
    "Pacific/Wallis" to "Wallis & Futuna Time",

    // The following were produced by ChatGPT and may therefore contain bullshit
    "Africa/Ceuta" to "Central European Time",
    "America/Araguaina" to "Brasília Time",
    "America/Argentina/Catamarca" to "Argentine Time",
    "America/Argentina/Cordoba" to "Argentine Time",
    "America/Argentina/Jujuy" to "Argentine Time",
    "America/Argentina/La_Rioja" to "Argentine Time",
    "America/Argentina/Mendoza" to "Argentine Time",
    "America/Argentina/Rio_Gallegos" to "Argentine Time",
    "America/Argentina/Salta" to "Argentine Time",
    "America/Argentina/San_Juan" to "Argentine Time",
    "America/Argentina/San_Luis" to "Argentine Time",
    "America/Argentina/Tucuman" to "Argentine Time",
    "America/Argentina/Ushuaia" to "Argentine Time",
    "America/Bahia" to "Brasília Time",
    "America/Belem" to "Brasília Time",
    "America/Boa_Vista" to "Amazon Time",
    "America/Boise" to "Mountain Time",
    "America/Cambridge_Bay" to "Mountain Time",
    "America/Campo_Grande" to "Amazon Time",
    "America/Chihuahua" to "Mountain Standard Time",
    "America/Coral_Harbour" to "Eastern Time",
    "America/Creston" to "Mountain Standard Time",
    "America/Cuiaba" to "Amazon Time",
    "America/Dawson" to "Pacific Time",
    "America/Dawson_Creek" to "Mountain Standard Time",
    "America/Detroit" to "Eastern Time",
    "America/Eirunepe" to "Acre Time",
    "America/Fort_Nelson" to "Mountain Standard Time",
    "America/Fortaleza" to "Brasília Time",
    "America/Glace_Bay" to "Atlantic Time",
    "America/Godthab" to "Western Greenland Time",
    "America/Goose_Bay" to "Atlantic Time",
    "America/Indiana/Indianapolis" to "Eastern Time",
    "America/Inuvik" to "Mountain Time",
    "America/Iqaluit" to "Eastern Time",
    "America/Juneau" to "Alaska Time",
    "America/Kentucky/Louisville" to "Eastern Time",
    "America/Maceio" to "Brasília Time",
    "America/Mazatlan" to "Mountain Standard Time",
    "America/Menominee" to "Central Time",
    "America/Merida" to "Central Time",
    "America/Moncton" to "Atlantic Time",
    "America/Monterrey" to "Central Standard Time",
    "America/Montreal" to "Eastern Time",
    "America/Nipigon" to "Eastern Time",
    "America/Nome" to "Alaska Time",
    "America/Ojinaga" to "Mountain Standard Time",
    "America/Pangnirtung" to "Eastern Time",
    "America/Porto_Velho" to "Western Amazon Time",
    "America/Rankin_Inlet" to "Central Time (North America)",
    "America/Recife" to "Brasília Time",
    "America/Resolute" to "Central Time (North America)",
    "America/Santarem" to "Brasília Time",
    "America/Sitka" to "Alaska Time",
    "America/Thunder_Bay" to "Eastern Time (North America)",
    "America/Yakutat" to "Alaska Time",
    "America/Yellowknife" to "Mountain Time (North America)",
    "Asia/Aqtau" to "Aqtau Time",
    "Asia/Atyrau" to "Atyrau Time",
    "Asia/Barnaul" to "Altai Time",
    "Asia/Choibalsan" to "Choibalsan Time",
    "Asia/Chongqing" to "China Standard Time",
    "Asia/Famagusta" to "Eastern European Time",
    "Asia/Gaza" to "Eastern European Time",
    "Asia/Harbin" to "China Standard Time",
    "Asia/Kashgar" to "China Standard Time",
    "Asia/Khandyga" to "Yakutsk Time",
    "Asia/Krasnoyarsk" to "Krasnoyarsk Time",
    "Asia/Kuching" to "Malaysia Time",
    "Asia/Magadan" to "Magadan Time",
    "Asia/Novokuznetsk" to "Kemerovo Time",
    "Asia/Oral" to "Oral Time",
    "Asia/Pontianak" to "Central Indonesian Time",
    "Asia/Qyzylorda" to "Qyzylorda Time",
    "Asia/Rangoon" to "Myanmar Time",
    "Asia/Samarkand" to "Uzbekistan Time",
    "Asia/Srednekolymsk" to "Srednekolymsk Time",
    "Asia/Tomsk" to "Tomsk Time",
    "Asia/Ust-Nera" to "Vladivostok Time",
    "Asia/Yakutsk" to "Yakutsk Time",
    "Atlantic/Madeira" to "Western European Time",
    "Australia/Broken_Hill" to "Central Time (Australia)",
    "Australia/Hobart" to "Australian Eastern Time",
    "Australia/Melbourne" to "Australian Eastern Time",
    "Europe/Astrakhan" to "Volga Time",
    "Europe/Kirov" to "Volga Time",
    "Europe/Saratov" to "Volga Time",
    "Europe/Ulyanovsk" to "Volga Time",
    "Europe/Uzhgorod" to "Central European Time",
    "Europe/Volgograd" to "Volgograd Time",
    "Europe/Zaporozhye" to "Eastern European Time",
    "Pacific/Pohnpei" to "Pohnpei Time",
)