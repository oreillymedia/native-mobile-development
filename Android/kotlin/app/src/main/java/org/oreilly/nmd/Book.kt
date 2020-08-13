package org.oreilly.nmd

data class Book(
    var title: String,
    var authors: MutableList<String>,
    var isbn: String,
    var pageCount: Int,
    var isFiction: Boolean
) {

    constructor() : this("", mutableListOf<String>(), "", 0, false) {
        // no op
    }

}
