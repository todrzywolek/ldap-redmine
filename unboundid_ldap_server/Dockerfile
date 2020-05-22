FROM java:7

ENV UNBOUNDID_HOME /opt/unboundid-ldap
ENV UNBOUNDID_VERSION unboundid-ldapsdk-3.1.0-se

ADD https://docs.ldap.com/ldap-sdk/files/${UNBOUNDID_VERSION}.zip unboundid-ldapsdk-se.zip
RUN mkdir -p ${UNBOUNDID_HOME}
RUN unzip unboundid-ldapsdk-se.zip -d ${UNBOUNDID_HOME}

ENTRYPOINT ./${UNBOUNDID_HOME}/${UNBOUNDID_VERSION}/tools/in-memory-directory-server \
    --baseDN 'dc=redmine2,dc=org' \
    --port 389 \
    --ldifFile ${UNBOUNDID_HOME}/conf/LDAP.ldif \
    --accessLogToStandardOut

EXPOSE 389