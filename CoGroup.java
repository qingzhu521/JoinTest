package TestCoGroup;


import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.FileSystem.WriteMode;
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
 * 		target/TestCoGroup-1.0-SNAPSHOT.jar
 * From the CLI you can then run
 * 		./bin/flink run -c TestCoGroup.BatchJob target/TestCoGroup-1.0-SNAPSHOT.jar
 *
 * For more information on the CLI see:
 *
 * http://flink.apache.org/docs/latest/apis/cli.html
 */
public class BatchJob {

	public static void main(String[] args) throws Exception {
		// set up the batch execution environment
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		ParameterTool param = ParameterTool.fromArgs(args);

		env.setParallelism(param.getInt("para"));
		DataSet<Integer> dx = env.readTextFile(param.get("a")).map(
			new MapFunction<String, Integer>() {
				@Override
				public Integer map(String value) throws Exception {
					return Integer.parseInt(value);
				}
			}
		);
		DataSet<Integer> dy = env.readTextFile(param.get("b")).map(
			new MapFunction<String, Integer>() {
				@Override
				public Integer map(String value) throws Exception {
					return Integer.parseInt(value);
				}
			}
		);
		DataSet<Tuple2<Integer, Integer>> dxy = dx.coGroup(dy).where("_").equalTo("_").with(
			new CoGroupFunction<Integer, Integer, Tuple2<Integer, Integer>>() {
				@Override
				public void coGroup(Iterable<Integer> first, Iterable<Integer> second,
									Collector<Tuple2<Integer, Integer>> out)
					throws Exception {
					int z = 0;
					for (int i : first){
						z = z + i;
					}
					for (int j : second){
						z = z + j;
					}
					out.collect(new Tuple2<Integer, Integer>(z,z));
				}
			}
		);

		// exe
		dxy.writeAsCsv("whatever"," ",System.lineSeparator(), WriteMode.OVERWRITE);
		env.execute("Flink Batch Java API Skeleton");
	}
}
