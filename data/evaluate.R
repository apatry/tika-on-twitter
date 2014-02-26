library(knitr)

                                        # load the tweets
tweets <- read.table("../big-tweets.csv", header=TRUE, sep=",")

                                        # build a contengency matrix
m <- table(tweets$twitter, tweets$tika)

                                        # compute statistics
languages <- unique(tweets$twitter)
stats <- data.frame(row.names=languages)

for (l in languages) {
    stats[l, "tp"] <- m[l, l];
    stats[l, "fp"] <- sum(m[,l]) - m[l, l]
    stats[l, "fn"] <- sum(m[l,]) - m[l, l]
}

                                        # compute precision, recall f-measure
stats$precision <- stats$tp / (stats$tp + stats$fp)
stats$recall <- stats$tp / (stats$tp + stats$fn)
stats$fmeasure <- 2 * stats$precision * stats$recall / (stats$precision + stats$recall)
stats$fmeasure[which(is.nan(stats$fmeasure))] <- 0
stats$size <- stats$tp + stats$fn

                                        # format table
kable(stats[order(rownames(stats)),][c("size", "precision", "recall", "fmeasure")], digits=2, format="markdown", row.names=TRUE)

accuracy <- sum(stats$tp)  / (sum(stats$tp) + sum(stats$fp))
print(sprintf("Accuracy %g", accuracy))
