# Tika on Twitter

Throw away code to evaluate Tika language detection on tweets.

## Usage

Once you have downloaded a corpus of tweets using
[twitter-sample](https://github.com/apatry/twitter-sampler), you can
run the language identifier using the following command:

```
lein run < tweets.json > tweets.csv
```

Where tweet.csv will look like [data/tweets.csv](data/tweets.csv).

## License

Copyright © 2014 Alexandre Patry

Distributed under the Eclipse Public License, the same as Clojure.
