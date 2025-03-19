plugins {
	alias(libs.plugins.android.application)
}

android {
	namespace = "com.websarva.wings.android.pagingremotemediatorjava"
	compileSdk = 35

	defaultConfig {
		applicationId = "com.websarva.wings.android.pagingremotemediatorjava"
		minSdk = 24
		targetSdk = 35
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	buildFeatures {
		viewBinding = true
	}
}

dependencies {
	implementation(libs.paging.runtime)
	implementation(libs.room.runtime)
	annotationProcessor(libs.androidx.room.compiler)
	implementation(libs.room.paging)
	implementation(libs.guava)
	implementation(libs.androidx.room.guava)
	implementation(libs.androidx.paging.guava)

	implementation(libs.appcompat)
	implementation(libs.material)
	implementation(libs.activity)
	implementation(libs.constraintlayout)
	testImplementation(libs.junit)
	androidTestImplementation(libs.ext.junit)
	androidTestImplementation(libs.espresso.core)
}