package com.venky.interview.`interface`

import com.venky.interview.datamodel.ListModel

interface ServerResult {
    fun success(result: ArrayList<ListModel>)
}