package zio.stream

import scala.{ Stream => _ }

import com.github.ghik.silencer.silent

import zio._
import zio.random.Random
import zio.stream.StreamChunkUtils._
import zio.test.Assertion.{ equalTo, isFalse, isLeft, succeeds }
import zio.test._

object StreamChunkSpec extends ZIOBaseSpec {

  def spec = suite("StreamChunkSpec")(
    // suite("StreamChunk.mapConcatM")(
    //   testM("mapConcatM happy path") {
    //     val fn = Gen.function[Random with Sized, Int, Iterable[Int]](Gen.listOf(intGen))
    //     checkM(pureStreamChunkGen(tinyChunks(intGen)), fn) { (s, f) =>
    //       for {
    //         res1 <- slurp(s.mapConcatM(s => UIO.succeedNow(f(s))))
    //         res2 <- slurp(s).map(_.flatMap(s => f(s).toSeq))
    //       } yield assert(res1)(equalTo(res2))
    //     }
    //   },
    //   testM("mapConcatM error") {
    //     StreamChunk
    //       .succeedNow(Chunk.single(1))
    //       .mapConcatM(_ => IO.failNow("Ouch"))
    //       .run(Sink.drain)
    //       .either
    //       .map(assert(_)(equalTo(Left("Ouch"))))
    //   }
    // ),
    // testM("StreamChunk.mapError") {
    //   StreamChunk(Stream.failNow("123"))
    //     .mapError(_.toInt)
    //     .run(Sink.drain)
    //     .either
    //     .map(assert(_)(isLeft(equalTo(123))))
    // },
    testM("StreamChunk.mapErrorCause") {
      StreamChunk(Stream.haltNow(Cause.fail("123")))
        .mapErrorCause(_.map(_.toInt))
        .run(Sink.drain)
        .either
        .map(assert(_)(isLeft(equalTo(123))))
    },
    // testM("StreamChunk.drop") {
    //   checkM(chunksOfInts, intGen) { (s, n) =>
    //     for {
    //       res1 <- slurp(s.drop(n))
    //       res2 <- slurp(s).map(_.drop(n))
    //     } yield assert(res1)(equalTo(res2))
    //   }
    // },
  )
}
