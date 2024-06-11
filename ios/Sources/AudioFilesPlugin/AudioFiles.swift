import Foundation

@objc public class AudioFiles: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
