let $stocks := (doc("stock.xml")/stocks)
return <results>
{    for $stock in $stocks/stock
        return $stock/company
}
</results>