package com.extensions.bundles

interface BundlifyType<E: Any> {
    var key: String
    var value: E
}