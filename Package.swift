// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorAudioFiles",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorAudioFiles",
            targets: ["AudioFilesPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "AudioFilesPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/AudioFilesPlugin"),
        .testTarget(
            name: "AudioFilesPluginTests",
            dependencies: ["AudioFilesPlugin"],
            path: "ios/Tests/AudioFilesPluginTests")
    ]
)