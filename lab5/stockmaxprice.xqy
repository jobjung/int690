let $stock := (doc("stock.xml")/stocks/stock)
return <results>
{  max($stock/values/value/price)
}
</results>