(defproject tika-on-twitter "0.1.0-SNAPSHOT"
  :description "Test driving Tika language detection against twitter"
  :url "http://textjuicer.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [cheshire "5.3.1"]
                 [org.apache.tika/tika-core "1.5"]]
  :main com.textjuicer.tikaontwitter.core)
