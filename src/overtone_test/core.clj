(ns overtone-test.core
  (:use clojure.pprint
        overtone.core
        overtone.inst.synth))


(use 'overtone.inst.piano)

(defonce server (boot-external-server))

;(piano (note :c5))
;(piano (note :e5))
;(piano (note :g5))

;; Note: You will need to have downloaded the piano samples
;; in order for this to work. TODO: where from???
(def piano-samples (load-samples "~/MIS_Stereo_Piano/Piano/*LOUD*"))
(defn matching-notes
  [note]
  (filter #(if-let [n (match-note (:name %))]
             (= note (:midi-note n)))
          piano-samples))

(defn sampled-piano
  ([note] (sampled-piano note 1))
  ([note vol]
     (if-let [sample (first (matching-notes note))]
       (stereo-player sample :vol vol))))



(def C [:C4 :Eb4 :G4 :Eb4])
(def Ab [:c4 :eb4 :ab4 :eb4])

(def notes (concat C Ab))

;(def saints [:c4 :e4 :f4 :g4 :g4 :g4 :g4 :g4])

(defn thing [x notes]
  (let [m (metronome x)]
    (doseq [i (range (count notes))]
      (let [n (note (notes i))]
        (at (m i) (sampled-piano n)))
      )


    ))

(defn thingie [n notes]
  )

(def repeated-notes
  (vec (apply concat  (repeat 32 notes))))

(thing 128 repeated-notes)
