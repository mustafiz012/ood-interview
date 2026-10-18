package grocery.discount.criteria

import grocery.Item

class CategoryBasedCriteria(private val category: String) : DiscountCriteria {
    override fun isApplicable(item: Item): Boolean {
        return item.category == category
    }
}
