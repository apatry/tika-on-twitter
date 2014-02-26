(ns com.textjuicer.tikaontwitter.core
  (:require [clojure.java.io :refer [reader writer]])
  (:require [cheshire.core :refer [parsed-seq generate-stream]])
  (:import org.apache.tika.language.LanguageIdentifier)
  (:gen-class))

(defn- create-identifier
  ^LanguageIdentifier [^String txt]
  (LanguageIdentifier. txt))

(defn detect-language
  "Detect the language of a text."
  [txt]
  (doto (create-identifier txt) (.getLanguage)))

(defn detect-tweet-language
  "Add tika-language and tika-confident (1 if tika is confident in its
  prediction, 0 otherwise) properties to the tweet."  [tweet]
  (let [li (LanguageIdentifier. ^String (get tweet "text"))
        lang (.getLanguage li)
        confident (.isReasonablyCertain li)]
    (into tweet {"tika-language" lang, "tika-confident" (if confident 1 0)})))

(defn supported-language?
  "Truthy when the language of the tweet is supported by tika."
  [tweet]
  (.contains (LanguageIdentifier/getSupportedLanguages) (get tweet "lang")))

(defn write-csv
  "Serialize a tweet into a CSV entry"
  [^java.io.Writer wtr tweets]
  (doseq [tweet tweets] 
    (.write wtr (format "%s,%s,%s,%d\n" 
                        (get tweet "id") 
                        (get tweet "lang") 
                        (get tweet "tika-language")
                        (get tweet "tika-confident")))))

(defn run
  "Process a seq of tweets from input and output csv entries to output
  containing tweet id, language from twitter, language from tika and a
  flag specifying if tika is confident in its prediction."  [input
  output]
  (with-open [rdr ^java.io.Reader (reader input)
              wtr ^java.io.Writer (writer output)]
    (.write wtr "id,twitter,tika,confident\n")
    (->> rdr 
         (parsed-seq)
         (filter supported-language?) 
         (map detect-tweet-language) 
         (write-csv wtr))))

(defn -main
  "Reads a stream of tweets from stdin and write a csv file to stdout."
  [& args]
  (run System/in System/out))