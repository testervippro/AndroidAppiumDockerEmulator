FROM ubuntu:latest
LABEL authors="ad"

ENTRYPOINT ["top", "-b"]