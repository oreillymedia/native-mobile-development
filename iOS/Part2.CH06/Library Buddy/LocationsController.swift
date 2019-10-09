//
//  LocationsController.swift
//  Library Buddy
//
//  Created by Shaun Lewis on 7/16/19.
//  Copyright © 2019 Shaun Lewis. All rights reserved.
//

import UIKit

class LocationsController {
    func fetchLocations(for country: String, completionHandler: @escaping ([Location]) -> (), errorHandler: @escaping (Error?) -> ()) {
        let url = URL(string: "http://localhost:5000/locations?country=\(country)")!
        let task = URLSession.shared.dataTask(with: url) { (data, response, error) in

            DispatchQueue.main.async {
                UIApplication.shared.isNetworkActivityIndicatorVisible = false
            }

            if let error = error {
                errorHandler(error)
                return
            }

            guard let response = response as? HTTPURLResponse, response.statusCode < 300 else {
                errorHandler(nil)
                return
            }

            guard let data = data, let locations = try? JSONDecoder().decode([Location].self, from: data) else {
                errorHandler(nil)
                return
            }

            // Call our completion handler with our locations
            completionHandler(locations)
        }

        DispatchQueue.main.async {
            UIApplication.shared.isNetworkActivityIndicatorVisible = true
        }
        task.resume()
    }
}

struct Location: Codable {
    let streetAddress: String
    let city: String
    let country: String
    let emoji: String
    let hours: String

    private enum CodingKeys: String, CodingKey {
        case streetAddress = "street_address"
        case city
        case country
        case emoji
        case hours
    }
}

