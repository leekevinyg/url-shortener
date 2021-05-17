package com.myorg.constructs;

import software.amazon.awscdk.services.ec2.IVpc;

public interface TrafficGeneratorProps {
    // Public constructor for the props builder
    public static Builder builder() {
        return new Builder();
    }

    IVpc getVpc();
    String getUrl();
    int getTps();

    // The builder for the props interface
    public static class Builder {
        private String url;
        private Integer tps;
        private IVpc vpc;

        public Builder vpc(IVpc vpc) {
            this.vpc = vpc;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder tps(int tps) {
            this.tps = tps;
            return this;
        }

        public TrafficGeneratorProps build() {
            if (this.vpc == null) {
                throw new NullPointerException("The VPC property is required!");
            }
            if (this.url == null) {
                throw new NullPointerException("The URL property is required!");
            }

            if (this.tps == null) {
                throw new NullPointerException("The tps (transactions per second) property is required!");
            }

            return new TrafficGeneratorProps() {
                @Override
                public IVpc getVpc() {
                    return vpc;
                }

                @Override
                public String getUrl() {
                    return url;
                }

                @Override
                public int getTps() {
                    return tps;
                }
            };
        }
    }
}