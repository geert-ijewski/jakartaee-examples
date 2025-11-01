FROM quay.io/wildfly/wildfly:latest

# Install curl + checkmk agent (Debian-based)
RUN microdnf install -y curl tar gzip shadow-utils && \
    curl -O https://download.checkmk.com/checkmk/2.3.0p11/check-mk-agent_2.3.0p11-1_all.deb && \
    tar -xf check-mk-agent_2.3.0p11-1_all.deb || true && \
    rm -f check-mk-agent_2.3.0p11-1_all.deb

# Alternatively, for RedHat-based base image:
# RUN curl -O https://download.checkmk.com/checkmk/2.3.0p11/check-mk-agent-2.3.0p11-1.noarch.rpm && \
#     rpm -Uvh check-mk-agent-2.3.0p11-1.noarch.rpm

# Checkmk agent port
EXPOSE 6556

# Start both WildFly and Checkmk agent (xinetd-like behavior)
RUN /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 &
RUN echo "Waiting for WildFly to start..."
RUN sleep 20
RUN /opt/jboss/wildfly/bin/jboss-cli.sh --connect --file=/opt/jboss/enable-health.cli
RUN /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command="/subsystem=deployment-scanner/scanner=default:write-attribute(name=scan-enabled,value=true)"
RUN  /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command="deployment-scanner=default:scan()"
RUN /usr/bin/check_mk_agent > /dev/tcp/0.0.0.0/6556 &
CMD ["/bin/tail", "-f", " /opt/jboss/wildfly/standalone/log/server.log"]
