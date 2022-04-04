# ktlint
checkstyle_format.base_path = Dir.pwd
Dir["**/build/reports/ktlint/*/*.xml"].each do |file|
  checkstyle_format.report file
end

# android lint
android_lint.skip_gradle_task = true # 事前にビルド実行するのでskip
android_lint.filtering = true
Dir["**/build/reports/lint-results*.xml"].each do |file|
  android_lint.report_file = file
  android_lint.lint(inline_mode: true)
end

# junit
Dir["**/build/test-results/*/*.xml"].each do |file|
  junit.parse file
  junit.show_skipped_tests = true
  junit.report
end