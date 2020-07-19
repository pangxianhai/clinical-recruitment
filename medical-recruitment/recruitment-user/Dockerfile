FROM alpine

COPY ./recruitment-user /data/workspace/recruitment-user

WORKDIR /data/workspace

RUN chmod +x recruitment-user

ENTRYPOINT ["./recruitment-user"]