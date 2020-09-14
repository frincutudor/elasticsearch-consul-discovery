package org.elasticsearch.plugin.discovery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.common.network.NetworkService;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.discovery.SeedHostsProvider;
import org.elasticsearch.discovery.consul.ConsulUnicastHostsProvider;
import org.elasticsearch.plugins.DiscoveryPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.transport.TransportService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
/**
 * Copyright © 2015 Lithium Technologies, Inc. All rights reserved subject to the terms of
 * the MIT License located at
 * <p>
 * <p>
 * LICENSE FILE DISCLOSURE STATEMENT AND COPYRIGHT NOTICE This LICENSE.txt file sets forth
 * the general licensing terms and attributions for the "Elasticsearch Consul Discovery
 * plugin" project software provided by Lithium Technologies, Inc. ("Lithium").  This
 * software is based upon certain software files authored by Grant Rodgers in 2015 as part
 * of the "Elasticsearch SRV discovery plugin" project.  As a result, some of the files in
 * this Elasticsearch Consul Discovery plugin" project software are wholly authored by
 * Lithium, some are wholly authored by Grant Rodgers, and others are originally authored
 * by Grant Rodgers and subsequently modified by Lithium.  Files that were either modified
 * or wholly authored by Lithium contain an additional LICENSE.txt file indicating whether
 * they were modified or wholly authored by Lithium.  Any LICENSE.txt files, copyrights or
 * attribution originally included in the files of the original "Elasticsearch SRV
 * discovery plugin" remain unchanged. Copyright Notices The following copyright notice
 * applies to only those files and modifications authored by Lithium: Copyright © 2015
 * Lithium Technologies, Inc.  All rights reserved subject to the terms of the MIT License
 * below. The following copyright notice applies to only those files in the original
 * "Elasticsearch SRV discovery plugin" project software excluding any modifications by
 * Lithium: Copyright (c) 2015 Grant Rodgers License The following MIT License, as
 * originally presented in the "Elasticsearch SRV discovery plugin" project software, also
 * applies to files authored or modified by Lithium.  Your use of the "Elasticsearch
 * Consul Discovery plugin" project software is therefore subject to the terms of the
 * following MIT License.  Except as may be granted herein or by separate express written
 * agreement, this file provides no license to any Lithium patents, trademarks,
 * copyrights, or other intellectual property. MIT License Permission is hereby granted,
 * free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to
 * whom the Software is furnished to do so, subject to the following conditions: The above
 * copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT
 * WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * <p>
 * <p>
 * <p>
 * Created by Jigar Joshi on 8/9/15.
 */
public class ConsulDiscoveryPlugin extends Plugin implements DiscoveryPlugin {
    private static Logger logger = LogManager.getLogger(ConsulDiscoveryPlugin.class);

    private final Settings settings;

    public ConsulDiscoveryPlugin(Settings settings) {
        this.settings = settings;
        logger.info("Starting Consul discovery plugin");
    }

    @Override
    public Map<String, Supplier<SeedHostsProvider>> getSeedHostProviders(TransportService transportService,
                                                                            NetworkService networkService) {
        return Collections.singletonMap(
                "consul",
                () -> {
                    return new ConsulUnicastHostsProvider(settings, transportService);
                });
    }

    @Override
    public List<Setting<?>> getSettings() {
        return Arrays.asList(
                ConsulUnicastHostsProvider.CONSUL_LOCALWSPORT,
                ConsulUnicastHostsProvider.CONSUL_LOCALWSHOST,
                ConsulUnicastHostsProvider.CONSUL_SERVICENAMES,
                ConsulUnicastHostsProvider.CONSUL_TAG,
                ConsulUnicastHostsProvider.CONSUL_HEALTHY
        );
    }
}
