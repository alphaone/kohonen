(ns kohonen.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [kohonen.core-test]
            [kohonen.kohonen-test]))

(doo-tests 'kohonen.core-test
           'kohonen.kohonen-test)