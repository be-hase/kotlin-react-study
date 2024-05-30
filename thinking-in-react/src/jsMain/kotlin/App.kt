import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.thead
import react.dom.html.ReactHTML.tr
import react.useState
import web.cssom.NamedColor
import web.html.InputType

external interface ProductCategoryRowProps : Props {
    var category: String
}

val ProductCategoryRow = FC<ProductCategoryRowProps> { props ->
    tr {
        th {
            colSpan = 2
            +props.category
        }
    }
}

external interface ProductRowProps : Props {
    var product: Product
}

val ProductRow = FC<ProductRowProps> { props ->
    tr {
        td {
            if (props.product.stocked) {
                +props.product.name
            } else {
                span {
                    css { color = NamedColor.red }
                    +props.product.name
                }
            }
        }
        td {
            +props.product.price
        }
    }
}

external interface ProductTableProps : Props {
    var products: List<Product>
    var filterText: String
    var inStockOnly: Boolean
}

val ProductTable = FC<ProductTableProps> { props ->
    table {
        thead {
            tr {
                th { +"Name" }
                th { +"Price" }
            }
        }
        tbody {
            var lastCategory: String? = null
            props.products
                .filter { product ->
                    props.filterText.isEmpty() || product.name.lowercase().contains(props.filterText.lowercase())
                }
                .filterNot { product ->
                    props.inStockOnly && !product.stocked
                }
                .forEach { product ->
                    if (product.category != lastCategory) {
                        ProductCategoryRow {
                            key = product.category
                            category = product.category
                        }
                    }
                    ProductRow {
                        key = product.name
                        this.product = product
                    }
                    lastCategory = product.category
                }
        }
    }
}

external interface SearchBarProps : Props {
    var filterText: String
    var inStockOnly: Boolean
    var onFilterTextChange: (String) -> Unit
    var onInStockOnlyChange: (Boolean) -> Unit
}

val SearchBar = FC<SearchBarProps> { props ->
    form {
        input {
            type = InputType.text
            value = props.filterText
            placeholder = "Search..."
            onChange = { props.onFilterTextChange(it.target.value) }
        }
        label {
            input {
                type = InputType.checkbox
                checked = props.inStockOnly
                onChange = { props.onInStockOnlyChange(it.target.checked) }
            }
            +"Only show products in stock"
        }
    }
}

external interface FilterableProductTableProps : Props {
    var products: List<Product>
}

val FilterableProductTable = FC<FilterableProductTableProps> { props ->
    var filterText: String by useState("")
    var inStockOnly: Boolean by useState(false)

    div {
        SearchBar {
            this.filterText = filterText
            this.inStockOnly = inStockOnly
            onFilterTextChange = {
                filterText = it
            }
            onInStockOnlyChange = {
                inStockOnly = it
            }
        }
        ProductTable {
            products = props.products
            this.filterText = filterText
            this.inStockOnly = inStockOnly
        }
    }
}

val App = FC {
    FilterableProductTable {
        products = PRODUCTS
    }
}

data class Product(
    val category: String,
    val price: String,
    val stocked: Boolean,
    val name: String,
)

val PRODUCTS = listOf(
    Product(category = "Fruits", price = "$1", stocked = true, name = "Apple"),
    Product(category = "Fruits", price = "$1", stocked = true, name = "Dragonfruit"),
    Product(category = "Fruits", price = "$2", stocked = false, name = "Passionfruit"),
    Product(category = "Vegetables", price = "$2", stocked = true, name = "Spinach"),
    Product(category = "Vegetables", price = "$4", stocked = false, name = "Pumpkin"),
    Product(category = "Vegetables", price = "$1", stocked = true, name = "Peas"),
)
