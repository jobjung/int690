let $stocks := (doc("stock.xml")/stocks)
return <results>
{   for $stock in $stocks/stock
    where $stock/values/value/price> 140
        return <comp> { $stock/company ,
                        for $v in  $stock/values/value
                        where $v/price > 140
                        return <valueAt> { $v/price,$v/date } </valueAt>} </comp>
}
</results>