package net.objecthunter.exp4j;

import net.objecthunter.exp4j.function.Factorial;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionsTest {
    @Test
    public void shouldCalculateFastLog2() {
        assertThat(Factorial.floorLog2(1)).isEqualTo(0);
        assertThat(Factorial.floorLog2(2)).isEqualTo(1);
        assertThat(Factorial.floorLog2(3)).isEqualTo(1);
        assertThat(Factorial.floorLog2(4)).isEqualTo(2);
        assertThat(Factorial.floorLog2(8)).isEqualTo(3);
        assertThat(Factorial.floorLog2(16)).isEqualTo(4);
        assertThat(Factorial.floorLog2(32)).isEqualTo(5);
        assertThat(Factorial.floorLog2(64)).isEqualTo(6);
        assertThat(Factorial.floorLog2(128)).isEqualTo(7);
        assertThat(Factorial.floorLog2(256)).isEqualTo(8);
        assertThat(Factorial.floorLog2(512)).isEqualTo(9);
        assertThat(Factorial.floorLog2(1024)).isEqualTo(10);
        assertThat(Factorial.floorLog2(2048)).isEqualTo(11);
        assertThat(Factorial.floorLog2( 4096)).isEqualTo(12);
        assertThat(Factorial.floorLog2( 6144)).isEqualTo(12);
    }

}
