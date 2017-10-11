package IterationCoGroupTest;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.CoGroupOperator.CoGroupOperatorSets;
import org.apache.flink.api.java.operators.DeltaIteration;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * Skeleton for a Flink Batch Job.
 *
 * For a full example of a Flink Batch Job, see the WordCountJob.java file in the
 * same package/directory or have a look at the website.
 *
 * You can also generate a .jar file that you can submit on your Flink
 * cluster.
 * Just type
 * 		mvn clean package
 * in the projects root directory.
 * You will find the jar in
 * 		target/IterationCoGroupTest-1.0-SNAPSHOT.jar
 * From the CLI you can then run
 * 		./bin/flink run -c IterationCoGroupTest.BatchJob target/IterationCoGroupTest-1.0-SNAPSHOT.jar
 *
 * For more information on the CLI see:
 *
 * http://flink.apache.org/docs/latest/apis/cli.html
 */
public class BatchJob {

	public static void main(String[] args) throws Exception {
		// set up the batch execution environment
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		 DataSet<Tuple2<Long, Long>> data = env.generateSequence(1,1000000).map(
            new MapFunction<Long, Tuple2<Long,Long>>() {
                @Override
                public Tuple2<Long,Long> map(Long value) throws Exception {
                    return new Tuple2<>(value,value);
                }
            }
        );

		DataSet<Long> fixset = env.generateSequence(1,200000005);

        DeltaIteration<Tuple2<Long, Long>, Tuple2<Long, Long>> diter = data.iterateDelta(data,3,0);

        DataSet<Tuple2<Long,Long>> delta = diter.getWorkset().coGroup(fixset).where(0).equalTo("_")
            .with(
                new CoGroupFunction<Tuple2<Long,Long>, Long, Tuple2<Long,Long>>() {
                    @Override
                    public void coGroup(Iterable<Tuple2<Long,Long>> first, Iterable<Long> second,
                                        Collector<Tuple2<Long,Long>> out)
                        throws Exception {
                        int cnt = 0;
                        for(Long i : second){
                            cnt++;
                        }
                        Long num = 0L;
                        Long n = 0L;
                        for (Tuple2<Long,Long> i : first){
                            n = i.f0;
                            num = i.f1;
                        }
                        if(cnt == 0){
                            out.collect(new Tuple2<Long,Long>(n, num + 1));
                        }
                    }
                }
            );

        DataSet<Tuple2<Long,Long>> nextworkset = delta;

        DataSet<Tuple2<Long,Long>> ans = diter.closeWith(delta, nextworkset);

        ans.sortPartition(0, Order.ASCENDING).print();

        //env.execute("Co");
	}
}
