for $years in (doc("NASAtemperatureAbnormal.xml")/years)
for $year in $years/year
     let $months := $year/months
     for $month in $months/*
      return  string-join(($year/@collectingYear/string(),
                $month/name(),
                $month/diffTemperature/text()
                ),",")


