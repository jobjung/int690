let $stocks := (doc("stock.xml")/stocks)
return <results>
{   for $stock in $stocks/stock
    where $stock/values/value/price > 140
        return <comp> { $stock/company,$stock/values/value/price } </comp>
}
</results>