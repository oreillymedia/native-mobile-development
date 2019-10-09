//
//  BookManagedObject+Extensions.swift
//  Library Buddy
//
//  Created by Shaun Lewis on 7/11/19.
//  Copyright © 2019 Shaun Lewis. All rights reserved.
//

import Foundation
import CoreData

extension BookManagedObject {
    var book: Book {
        return Book(title: title!, authors: authors!, isbn: isbn!, pageCount: Int(pageCount), fiction: fiction)
    }

    class func newCatalogResultsController(with dataController: DataController, delegate: NSFetchedResultsControllerDelegate?) -> NSFetchedResultsController<BookManagedObject> {
        let request = NSFetchRequest<BookManagedObject>(entityName: "Book")

        // Add a sort by title description to the fetch request
        let titleSort = NSSortDescriptor(key: "title", ascending: true)
        request.sortDescriptors = [titleSort]

        let context = dataController.persistentContainer.viewContext
        let fetchedResultsController = NSFetchedResultsController(fetchRequest: request, managedObjectContext: context, sectionNameKeyPath: nil, cacheName: "catalog.cache")

        // Assign the delegate to handle updates
        fetchedResultsController.delegate = delegate

        do {
            try fetchedResultsController.performFetch()
        } catch {
            fatalError("Catalog fetch could not be completed. \(error)")
        }

        return fetchedResultsController
    }
}
