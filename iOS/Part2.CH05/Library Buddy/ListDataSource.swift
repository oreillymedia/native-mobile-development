//
//  ListDataSource.swift
//  Library Buddy
//
//  Created by Shaun Lewis on 7/2/19.
//  Copyright © 2019 Shaun Lewis. All rights reserved.
//

import UIKit
import CoreData

class ListDataSource: NSObject {
    var fetchedResultsController: NSFetchedResultsController<BookManagedObject>?

    func fetchCatalogResults(with dataController: DataController, delegate: NSFetchedResultsControllerDelegate?) {
        fetchedResultsController = BookManagedObject.newCatalogResultsController(with: dataController, delegate: delegate)
    }

    func book(for indexPath: IndexPath) -> Book? {
        return fetchedResultsController?.object(at: indexPath).book
    }
}
extension ListDataSource: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return fetchedResultsController?.sections?[section].numberOfObjects ?? 0
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        // Dequeue a table view cell
        let cell = tableView.dequeueReusableCell(withIdentifier: "CatalogTableViewCell", for: indexPath)

        // Find the correct book based on the row being populated
        guard let book = fetchedResultsController?.object(at: indexPath) else {
            fatalError("Could not retrieve book instance.")
        }

        // Populate the table view cell title label with the book title
        cell.textLabel?.text = book.title

        return cell
    }
}
