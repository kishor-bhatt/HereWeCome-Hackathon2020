package com.here.mwv.data.catalog;


/*
 * Copyright (c) 2018-2019 HERE Europe B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static java.lang.System.out;

import akka.actor.ActorSystem;
import akka.actor.CoordinatedShutdown;
import com.here.hrn.HRN;
import com.here.platform.data.client.javadsl.DataClient;
import com.here.platform.data.client.javadsl.QueryApi;
import com.here.platform.data.client.model.VersionDependency;
import java.util.OptionalLong;

public class Catalog {

   public static void main(String[] args) {

        // Initialize the Akka Actor System used by the Data Client Library
        ActorSystem actorSystem = ActorSystem.create();

        // The Here Map Content identifier is an HRN (Here Resource Name)
        // Please, use hrn:here-cn:data:::here-map-content-china-2 for China environment
        HRN hereMapContentHrn = HRN.fromString("hrn:here:data::olp-here:rib-2");

        out.println("Partition :-"+hereMapContentHrn.partition());



        // Initialize the query API for the catalog
        QueryApi queryApi = DataClient.get(actorSystem).queryApi(hereMapContentHrn);

        // Get the latest available catalog version
        queryApi
                .getLatestVersion(OptionalLong.empty())
                .thenAccept(
                        version -> {
                            if (version.isPresent()) {
                                out.println("The latest Here Map Content version is " + version.getAsLong());
                                queryApi
                                        .getVersion(version.getAsLong())
                                        .thenAccept(
                                                versionInfo -> {
                                                    out.println(
                                                            "The catalog was last updated on "
                                                                    + new java.util.Date(versionInfo.getTimestamp()));
                                                    out.println("And was reportedly compiled out of this input catalogs:");
                                                    versionInfo
                                                            .getDependencies()
                                                            .stream()
                                                            .filter(VersionDependency::isDirect)
                                                            .forEach(
                                                                    dependency ->
                                                                            out.println(
                                                                                    "  "
                                                                                            + dependency.getHrn()
                                                                                            + " "
                                                                                            + dependency.getVersion()));
                                                })
                                        .toCompletableFuture()
                                        .join();
                            } else {
                                out.println("No version for this catalog");
                            }
                        })
                .toCompletableFuture()
                .join();

        // In production code this would be in a finally block
        CoordinatedShutdown.get(actorSystem)
                .runAll(CoordinatedShutdown.unknownReason())
                .toCompletableFuture()
                .join();
    }


   private void createCatalog(){

   }

   private void getData(){

   }


}
