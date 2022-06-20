package com.youness.mowerkata;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTests {

    @Test
    public void it_should_read_file_lines() {
        String[] args = new String[]{"test.txt"};
        List<String> fileLines = Application.readInstructionsFile(args[0]);
        assertThat(fileLines.size()).isEven();
        assertThat(fileLines.size()).isEqualTo(10);
    }

    @Test
    public void it_should_read_file_and_remove_empty_lines() {
    }


}
