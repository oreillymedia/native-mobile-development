//
//  ListDataSource.swift
//  Library Buddy
//
//  Created by Shaun Lewis on 7/2/19.
//  Copyright © 2019 Shaun Lewis. All rights reserved.
//

import UIKit

class ListDataSource: NSObject {
    lazy var data: [Book] = {
        do {
            guard let rawCatalogData = try? Data(contentsOf: Bundle.main.bundleURL.appendingPathComponent("catalog.json")) else {
                return []
            }
            return try JSONDecoder().decode([Book].self, from: rawCatalogData)
        } catch {
            print("Catalog.json was not found or is not decodable.")
        }
        return []
    }()
    
}
extension ListDataSource: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        // Dequeue a table view cell
        let cell = tableView.dequeueReusableCell(withIdentifier: "CatalogTableViewCell", for: indexPath)

        // Find the correct book based on the row being populated
        let book = data[indexPath.row]

        // Populate the table view cell title label with the book title
        cell.textLabel?.text = book.title

        return cell
    }
}
