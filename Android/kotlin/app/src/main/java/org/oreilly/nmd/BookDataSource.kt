package org.oreilly.nmd

interface BookDataSource {
    operator fun get(index: Int): Book
    val size:Int
}
