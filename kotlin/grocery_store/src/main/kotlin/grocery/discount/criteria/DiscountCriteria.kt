package grocery.discount.criteria

import grocery.Item

fun interface DiscountCriteria {
    fun isApplicable(item: Item): Boolean
}
