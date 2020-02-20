package extra.kotlin.collection


infix fun Iterable<*>.contentEquals(other: Iterable<*>): Boolean{
    val otherIter = other.iterator()
    for(item in this){
        if(!otherIter.hasNext()) return false
        if(item != otherIter.next()) return false
    }
    return true
}
infix fun Collection<*>.contentEquals(other: Collection<*>): Boolean{
    if(this.size != other.size) return false
    return (this as Iterable<*>).contentEquals(other)
}
