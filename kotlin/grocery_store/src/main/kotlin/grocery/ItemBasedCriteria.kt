package grocery

import grocery.discount.criteria.DiscountCriteria

class ItemBasedCriteria(private val itemId: String) : DiscountCriteria {
    override fun isApplicable(item: Item): Boolean {
        return item.barcode == itemId
    }
}
