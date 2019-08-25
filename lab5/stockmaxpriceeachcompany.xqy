for $stock in (doc("stock.xml")/stocks/stock)
let $price := $stock/values/value/price
return <results>
{
    $stock/company/abbreviattion , <maxprice> { max($price) } </maxprice>
}
</results>