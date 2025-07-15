FROM budtmo/docker-android:emulator_11.0

USER root

RUN echo 'root:x:0:0:root:/root:/bin/bash' >> /etc/passwd

USER androidusr

COPY run.sh /run.sh
RUN chmod +x /run.sh

ENTRYPOINT ["/bin/bash", "-c"]
CMD ["/run.sh"]