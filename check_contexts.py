from appium import webdriver
from appium.options.android import UiAutomator2Options

options = UiAutomator2Options()
options.platform_name = 'Android'
options.udid = 'adb-345311634900047-71AzuX'
options.app_package = 'com.indomaret.klikindomaret'
options.app_activity = 'com.indomaret.klikindomaret.MainActivity'
options.no_reset = True

driver = webdriver.Remote('http://127.0.0.1:4723', options=options)
print("Available contexts:", driver.contexts)
print("Current context:", driver.current_context)
driver.quit()
