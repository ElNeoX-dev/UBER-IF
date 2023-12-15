#!/bin/bash

# Base directory for the main source files
BASE_SRC_DIR="src/main/java/com/malveillance/uberif"

# Base directory for the test source files
TEST_SRC_DIR="src/test/java/com/malveillance/uberif"

# Create the test source directory if it doesn't exist
mkdir -p "$TEST_SRC_DIR"

# Function to create test classes
create_test_classes() {
  for java_file in $(find "$BASE_SRC_DIR/$1" -name "*.java"); do
    # Extract the class name without the .java extension
    class_name=$(basename "$java_file" .java)
    # Create the test class name by appending Test
    test_class_name="${class_name}Test"
    # Create the directory structure in the test source directory
    test_dir=$(dirname "$java_file" | sed "s|$BASE_SRC_DIR|$TEST_SRC_DIR|")
    mkdir -p "$test_dir"
    # Create an empty test class file
    test_class_file="$test_dir/$test_class_name.java"
    if [ ! -f "$test_class_file" ]; then
      echo "Creating test class for $class_name: $test_class_file"
      cat > "$test_class_file" <<EOF
package $(echo "$test_dir" | sed "s|/|.|g;s|src.test.java.||");

import org.junit.jupiter.api.Test;

class ${test_class_name} {

    @Test
    void testMethod() {
        // TODO: Implement test cases
    }
}
EOF
    else
      echo "Test class already exists: $test_class_file"
    fi
  done
}

# Recursively create test classes for each sub-package
create_test_classes

echo "Test class creation script completed."

