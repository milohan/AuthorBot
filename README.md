AuthorBot is an author-discovery web app.
First AuthorBot chooses a random author from a database* of authors.  It then analyzes the author's writing style using a markov chain word frequency algorithm.  Based of this analysis, it then imitates the author's writing style and starts speaking prose to the user using ResponsiveVoice.js.  In this way, the user gets a sense of the writing style of the author without having any plot points ruined from actual books that the author has written.

*AuthorBot was originally coded to scrape books/authors from gutenberg.org, but it turned out that gutenberg really didn't like that.  AuthorBot now works with a backend server that stores a limited number of public domain books/authors.
