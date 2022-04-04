# ktlint
checkstyle_format.base_path = Dir.pwd
Dir["**/reports/ktlint/ktlintMainSourceSetCheck/ktlintMainSourceSetCheck.xml"].each do |file|
  checkstyle_format.report file
end

# android lint
android_lint.skip_gradle_task = true # 事前にビルド実行する
# android_lint.filtering = true # 一旦確認のためfilterを外す
Dir["*/build/reports/lint-results*.xml"].each do |file|
  android_lint.report_file = file
  android_lint.lint(inline_mode: true)
end