package extra.kotlin.util

//@ExperimentalUnsignedTypes
//class UUID(val msb: Long, val lsb: Long) {
//
//    override fun toString() = buildString {
//        val msbBytes = msb.getBytes()
//        for(i in 0 until 8){
//            append(msbBytes[i].toHexChars())
//            if(i == 3 || i == 5 || i == 7)
//                append('-')
//        }
//        val lsbBytes = lsb.getBytes()
//        for(i in 0 until 8){
//            append(lsbBytes[i].toHexChars())
//            if(i == 1)
//                append('-')
//        }
//    }
//
//}