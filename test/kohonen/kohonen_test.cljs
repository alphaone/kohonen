(ns kohonen.kohonen-test
  (:require [cljs.test :refer-macros [deftest is testing]]
            [kohonen.kohonen :as k]))

(deftest dist-test
  (testing "it calcs the distance between to points"
    (is (= 100
           (k/dist {:x 0 :y 0} {:x 0 :y 100})))
    (is (= 200
           (k/dist {:x 200 :y 0} {:x 0 :y 0})))
    (is (= (k/dist {:x 0 :y 100} {:x 100 :y 0})
           (k/dist {:x 0 :y 0} {:x 100 :y 100})))))

(deftest apply-stimulus-test
  (is (= [{:x 100 :y 100}]
         (k/apply-stimulus {:x 100 :y 100} [{:x 100 :y 100}]))))

(deftest find-nearest-test
  (testing "it updates acc if new min distance is found"
    (is (= {:nearest-index 2 :nearest {:x 100 :y 100} :min-dist 0}
           (k/find-nearest {:x 100 :y 100} {:nearest-index -1 :nearest {} :min-dist 200} [2 {:x 100 :y 100}]))))
  (testing "it does not update acc if no new min distance is found"
    (is (= {:nearest-index 1 :nearest {} :min-dist 0}
           (k/find-nearest {:x 100 :y 100} {:nearest-index 1 :nearest {} :min-dist 0} [2 {:x 200 :y 200}])))))

(deftest nearest-test
  (testing "it finds the index of the neuron with the best matching weights"
    (is (= [0 {:x 100 :y 100}]
           (k/nearest {:x 100 :y 100} [{:x 100 :y 100}])))
    (is (= [1 {:x 100 :y 100}]
           (k/nearest {:x 100 :y 100} [{:x 0 :y 0} {:x 100 :y 100}])))
    (is (= [2 {:x 95 :y 90}]
           (k/nearest {:x 100 :y 100} [{:x 0 :y 0} {:x 50 :y 100} {:x 95 :y 90}])))
    (is (= [1 {:x 95 :y 90}]
           (k/nearest {:x 100 :y 100} [{:x 0 :y 0} {:x 95 :y 90} {:x 50 :y 100}])))))

(deftest adaption-factors-test
  (let [factors (k/adaption-factors 15 99)]
    (testing "it returns a vector of given length"
      (is (= 99
             (count factors))))
    (testing "it returns a vector where pos i has the max"
      (is (= (nth factors 15)
             (apply max factors))))
    (testing "?"
      (is (> (nth factors 15)
             (nth factors 14)))
      (is (> (nth factors 15)
             (nth factors 16))))))

(deftest normal-distribution-test
  (testing "standard normal distribution"
    (is (= 0.24197072451914337
           (k/normal-distribution -1 0 1)))
    (is (= 0.3989422804014327
           (k/normal-distribution 0 0 1)))
    (is (= 0.24197072451914337
           (k/normal-distribution 1 0 1)))
    (is (= 0.05399096651318806
           (k/normal-distribution 2 0 1))))
  (testing "mit µ=-1"
    (is (=  0.24197072451914337
            (k/normal-distribution -2 -1 1)))
    (is (= 0.3989422804014327
           (k/normal-distribution -1 -1 1)))
    (is (=  0.24197072451914337
           (k/normal-distribution 0 -1 1)))))