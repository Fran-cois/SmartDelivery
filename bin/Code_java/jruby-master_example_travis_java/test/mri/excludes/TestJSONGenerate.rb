exclude :test_broken_bignum, "fails in travis but not locally?"
exclude :test_gc, "needs investigation"
exclude :test_hash_likeness_set_string, "needs investigation"
exclude :test_hash_likeness_set_symbol, "needs investigation"
exclude :test_string_subclass, "hard call of json blows up; probably fixed in C ext. https://github.com/flori/json/issues/272"
