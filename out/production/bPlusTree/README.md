Query syntax from assignment 1 has been changed to cater to range search requirements.

The B+ tree will index for the value durationSeconds, hence queries will be exclusively for durationSeconds.

The query should now be called using syntax below:
java dbquery range gt500 4096
where:
gt means greater than
lt means less than (can be used in place of greater than)
500 indicates we want all records with durationSeconds value greater than 500
4096 is the page file

The option range can also be substituted for equality if you need an equality search.
